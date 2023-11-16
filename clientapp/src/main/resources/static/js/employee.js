$(document).ready(function () {
  $("#table-employee").DataTable({
    ajax: {
      method: "GET",
      url: `/api/employee${new URLSearchParams(window.location.search).has('role') ? '?role='+new URLSearchParams(window.location.search).get('role') : ''}`,
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: function (data, type, row, meta) {
          // Menghitung indeks berdasarkan posisi dalam data
          return meta.row + 1;
        },
      },
      {data: "name"},
      {data: "email"},
      {data: "phone"},
      {
        data: null,
        render: (data) => {
          return `
              <div class="flex justify-start gap-2">
                <!-- Button detail modal -->
                <button
                data-modal-target="detail"
                data-modal-toggle="detail"
                class="btn btn-warning btn-sm"
                employeeId="${data.id}"
                onclick="employee_detail(${data.id})"
                title="Detail-employee"
                >
                  <ion-icon name="information-circle" size="large" class="text-blue-500"></ion-icon>
                </button>
              <!-- Button update modal -->
                <a
                  href="http://localhost:9090/employee/update/${data.id}"
                  class="btn btn-warning btn-sm"
                  title = "Update-Employee"
                >
                  <ion-icon name="create" size="large" class="text-yellow-500"></ion-icon>
              </a>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  employeeId="${data.id}"
                  onclick="deleteEmployee(this)"
                  title = "Delete-Employee"
                >
                  <ion-icon name="trash" size="large" class="text-red-500"></ion-icon>
                </button>
              </div>
            `;
        },
      },
    ],
  });
});

function employee_detail(id){
  window.location.href='/employee/' + id;
}

function showToast(type, text) {
  return Swal.fire({
    toast: true,
    position: "top-end",
    showConfirmButton: false,
    timer: 2000,
    icon: type,
    title: text,
  });
}

$('#createEmployeeButton').one('click', (event) => {
  event.preventDefault()
  createEmployee()
})

$('#updateEmployeeButton').one("click", (event) => {
  event.preventDefault()
  let id = $('#updateEmployeeId').val()
  $.ajax({
    method: "PUT",
    url: `/api/employee/${id}`,
    dataType: "JSON",
    contentType: "application/json",
    data: JSON.stringify({
      name: $('#updateEmployeeName').val(),
      phone: $('#updateEmployeePhone').val(),
      email: $('#updateEmployeeEmail').val(),
      address: $('#updateEmployeeAddress').val(),
      jobPosition: $('#updateEmployeeJobPosition').val(),
      roleIds: [$('#roleUpdateSelection').val()],
      username: $('#updateEmployeeUsername').val(),
      password: $('#updateEmployeePassword').val()
    }),
    beforeSend : function () {
      setCsrf();
    },
    success: (res) => {
      $("#updateEmployeeModal").hide();
      showToast("success", "Employee updated successfully").then(() => history.back());

    },
    error: (error) => {
      let errorJsn = error.responseJSON
      showToast("error", errorJsn.message).then(() => location.reload());
    },
  });
})

function createEmployee() {
  let employeeName = $("#inputEmployeeName").val();
  let employeePhone = $("#inputEmployeePhone").val();
  let employeeEmail = $("#inputEmployeeEmail").val();
  let employeeAddress = $("#inputEmployeeAddress").val();
  let employeeJobPosition = $("#inputEmployeeJobPosition").val();
  let employeeUsername = $("#inputEmployeeUsername").val();
  let employeePassword = $("#inputEmployeePassword").val();

  let newData = {
    name: employeeName,
    email: employeeEmail,
    phone: employeePhone,
    address: employeeAddress,
    jobPosition: employeeJobPosition,
    username: employeeUsername,
    password: employeePassword,
  };

  $.ajax({
    type: "POST",
    url: "api/employee",
    contentType: "application/json",
    data: JSON.stringify(newData),
    beforeSend: function () {
      setCsrf()
    }, //buat headers
    success: function (response) {
      $("#table-employee").DataTable().ajax.reload();
      $("#inputEmployeeName").val('');
      $("#inputEmployeePhone").val('');
      $("#inputEmployeeEmail").val('');
      $("#inputEmployeeAddress").val('');
      $("#inputEmployeeJobPosition").val('');
      $("#inputEmployeeUsername").val('');
      $("#inputEmployeePassword").val('');
      showToast("success", "Employee added successfully");
    },
    error: function (error) {
      showToast("error", "Failed to create employee");
    },
  });
}

function deleteEmployee(button) {
  let id = button.getAttribute('employeeId')
  Swal.fire({
    title: `Are you sure want to delete training ?`,
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, delete it!'
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        method: "DELETE",
        url: `api/employee/${id}`,
        dataType: "JSON",
        contentType: "application/json",
        beforeSend: function () {
          setCsrf()
        },
        success: (res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Employee successfully deleted...",
            showConfirmButton: false,
            timer: 2000,
          });
          $("#table-employee").DataTable().ajax.reload();
        },
        error: (err) => {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Something is wrong !!!",
          });
        },
      });
    }
  })
}

$('#changePasswordBtn').click((event) => {
  event.preventDefault()
  let oldPassword = $('#changePasswordOldPassword').val()
  let newPassword = $('#changePasswordNewPassword').val()
  let newConfirmationPassword = $('#changePasswordNewConfirmationPassword').val()
  if (newPassword !== newConfirmationPassword) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "New password not matching !!!",
    });
  } else {
    $.ajax({
      method: "PUT",
      url: "/api/employee/change-password",
      dataType: "JSON",
      contentType: "application/json",
      data: JSON.stringify({
        oldPassword: oldPassword,
        newPassword: newPassword,
      }),
      beforeSend: function () {
        setHeaders()
      },
      success: (res) => {
        $('#changePasswordOldPassword').val('')
        $('#changePasswordNewPassword').val('')
        $('#changePasswordNewConfirmationPassword').val('')
        showToast("success", "Employee updated successfully").then(() => location.href = '/profile');
      },
      error: (err) => {
        let errorJson = err.responseJSON;
        showToast("error", errorJson.message);
      },
    });
  }
})

$('#updateProfileBtn').one("click", (event) => {
  event.preventDefault()
  let id = $('#updateProfileId').val()
  $.ajax({
    method: "PUT",
    url: `/api/employee/profile/update/${id}`,
    dataType: "JSON",
    contentType: "application/json",
    data: JSON.stringify({
      name: $('#updateProfileName').val(),
      phone: $('#updateProfilePhone').val(),
      email: $('#updateProfileEmail').val(),
      address: $('#updateProfileAddress').val(),
      username: $('#updateProfileUsername').val()
    }),
    beforeSend : function () {
      setCsrf();
    },
    success: (res) => {
      $("#updateEmployeeModal").hide();
      showToast("success", "Profile updated successfully").then(() => location.href = '/profile');

    },
    error: (error) => {
      let errorJsn = error.responseJSON
      showToast("error", errorJsn.message).then(() => location.reload());
    },
  });
})
