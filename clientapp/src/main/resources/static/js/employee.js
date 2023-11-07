function showEmployeeDetail(button) {
  let employeeId = $(button).data("employeeId");

  // Make an AJAX request to fetch employee details by ID
  $.ajax({
    method: "GET",
    url: `/api/employee/${employeeId}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      console.log(res);
      // Populate the modal with employee details
      $('#employeeDetailModalLabel').text(res.name);
      $('#employeeId').text(res.id);
      $('#employeeName').text(res.name);
      $('#employeePhone').text(res.phone);
      $('#employeeEmail').text(res.email);
      $('#employeeAddress').text(res.address); // Add address
      $('#employeeJobPosition').text(res.jobPosition); // Add job position
      $('#employeeUserName').text(res.user.username);
      $('#employeeRolesName').text(res.user.roles[0].name); // Add role name

      // Show the modal
      $("#employeeDetailModal").modal("show");
    },
    error: (err) => {
      console.log(err);
    },
  });
}


$(document).ready(function () {
  $("#table-employee").DataTable({
    ajax: {
      method: "GET",
      url: "api/employee",
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
      { data: "name" },
      { data: "email" },
      { data: "phone" },
      {
        data: null,
        render: (data) => {
          return `
              <div class="d-flex justify-content-center gap-3">
                <!-- Button detail modal -->
                <button
                data-modal-target="detail"
                data-modal-toggle="detail"
                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                type="submit"
                data-employeeId="${data.id}"
                onclick="showEmployeeDetail(this)"
                title="Detail ${data.name}"
              >
                <ion-icon name="information-circle" size="large"></ion-icon>
              </button>

                <!-- Button update modal -->
                <button
                  type="button"
                  class="btn btn-warning btn-sm"
                  data-bs-toggle="modal"
                  data-bs-target="#updateRegionModal"
                  data-id="${data.id}"
                  data-name="${data.name}"
                  title="Update ${data.name}"
                  onclick="openUpdateModal(this)"
                >
                  <ion-icon name="create" size="large"></ion-icon>
                </button>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  title="Delete ${data.name}"
                  regionId="${data.id}"
                  regionName="${data.name}"
                  onclick="confirmDelete(this)"
                >
                  <ion-icon name="trash" size="large"></ion-icon>
                </button>
              </div>
            `;
        },
      },
    ],
  });
});

function showToast(type, text) {
  Swal.fire({
    toast: true,
    position: "top-end",
    showConfirmButton: false,
    timer: 3000,
    icon: type,
    title: text,
  });
}

function openCreateEmployeeModal() {
  document.getElementById("create-from").reset();
  $("#CreateEmployee").modal("show");
}

function createEmployee() {
  let employeeName = $("#create-name").val();
  let employeePhone = $("#create-phone").val();
  let employeeEmail = $("#create-email").val();
  let employeeAddress = $("#create-address").val();
  let employeeJobPosition = $("#create-jobPosition").val();
  let employeeUsername = $("#create-username").val();
  let employeePassword = $("#create-password").val();

  let newData = {
    name: employeeName,
    email: employeeEmail,
    phone: employeePhone,
    phone: employeeAddress,
    phone: employeeJobPosition,
    username: employeeUsername,
    password: employeePassword,
  };
  console.log(newData);

  $.ajax({
    type: "POST",
    url: "api/employee",
    contentType: "application/json",
    data: JSON.stringify(newData),
    beforeSend: setHeaders(), //buat headers
    success: function (response) {
      $("#create").modal("hide");
      $("#table-employee").DataTable().ajax.reload();
      showToast("success", "Employee added successfully");
    },
    error: function (error) {
      showToast("error", "Failed to create employee");
    },
  });
}
