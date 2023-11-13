$(document).ready(function () {
  let trainingRegisterSection = document.getElementById(
    "trainingRegisterSection"
  );
  let authorities;
  if (trainingRegisterSection !== null) {
    authorities = trainingRegisterSection.getAttribute("authorities");
  }
  let dataTable = $("#table-training-registration").DataTable({
    ajax: {
      method: "GET",
      url: "/api/trainings/register",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: function (data, type, row, meta) {
          return meta.row + 1;
        },
      },
      { data: "training.title" },
      {
        data: null,
        render: (data) => {
          return data.trainee.user.username;
        },
      },
      {
        data: null,
        render: function (data, type, row, meta) {
          let checkboxSuccessId = `checkbox-success-${meta.row}`;
          let checkboxPendingId = `checkbox-pending-${meta.row}`;
          let checkboxRejectId = `checkbox-reject-${meta.row}`;

          return `
            <div class="flex items-center justify-center space-x-4">
              <div>
                <label for="${checkboxSuccessId}">Sukses</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxSuccessId}"
                  ${data.currentStatus.id === 1 ? "checked" : ""}
                  onchange="updateStatus(${data.id}, 1)"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                >
              </div>
              <div>
                <label for="${checkboxPendingId}">Pending</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxPendingId}"
                  ${data.currentStatus.id === 2 ? "checked" : ""}
                  onchange="updateStatus(${data.id}, 2)"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                >
              </div>
              <div>
                <label for="${checkboxRejectId}">Reject</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxRejectId}"
                  ${data.currentStatus.id === 3 ? "checked" : ""}
                  onchange="updateStatus(${data.id}, 3)"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                >
              </div>
            </div>
          `;
        },
      },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
              <div class="flex justify-center gap-3">
                <button
                    type="button"
                    class="btn btn-warning btn-sm"
                    registrationId="${data.id}"
                    onclick="downloadAttachment(this)"
                    title="Download attachment"
                  >
                  <ion-icon name="download" size="large" class="text-green-500"></ion-icon>
                </button>
                <!-- Button cancel -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  registrationId="${data.id}"
                  onclick="cancelTrainingRegistration(this)"
                  ${authorities.includes("TRAINEE") ? "" : "hidden"}
                >
                  <ion-icon name="arrow-undo-circle" size="large" class="text-red-500"></ion-icon>
                </button>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  registrationId="${data.id}"
                  onclick="deleteTrainingRegistration(this)"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
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


function updateStatus(registrationId, newStatus) {
  // Dapatkan nilai isChecked
  let isChecked = $(`#checkbox-${newStatus}-${registrationId}`).prop("checked");

  // Panggil fungsi untuk mengirim permintaan Ajax untuk memperbarui status di database
  updateStatusInDatabase(registrationId, newStatus, isChecked);
}

function getStatusColors(statusId) {
  switch (statusId) {
    case 1:
      return { bgColor: "green" };
    case 2:
      return { bgColor: "yellow" };
    case 3:
    case 4:
      return { bgColor: "red" };
    case 5:
      return { bgColor: "blue" };
    default:
      return { bgColor: "black" };
  }
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

function cancelTrainingRegistration(button) {
  let id = button.getAttribute("registrationId");
  Swal.fire({
    title: `Are you sure want to cancel training ?`,
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, cancel !",
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        method: "POST",
        url: `/api/trainings/register/cancel/${id}`,
        dataType: "JSON",
        contentType: "application/json",
        beforeSend: function () {
          setCsrf();
        },
        success: (res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Registration request to cancel...",
            showConfirmButton: false,
            timer: 2000,
          });
          $("#table-training-registration").DataTable().ajax.reload();
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
  });
}

function downloadAttachment(button) {
  let id = button.getAttribute("registrationId");
  $.ajax({
    method: "GET",
    url: `/api/trainings/register/attachment/${id}`,
    contentType: "application/pdf",
    success: (response) => {
      console.log(response);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function setStatus() {
  $.ajax({
    method: "GET",
    url: `/api/status`,
    dataType: "JSON",
    contentType: "application/json",
    success: (response) => {
      $.each(response, (index, value) => {
        $(`#statusSelection`).append(
          `<option value="${value.id}">${value.name}</option>`
        );
      });
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function deleteTrainingRegistration(button) {
  let id = button.getAttribute("registrationId");
  Swal.fire({
    title: `Are you sure want to delete training ?`,
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!",
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        method: "DELETE",
        url: `/api/trainings/register/${id}`,
        dataType: "JSON",
        contentType: "application/json",
        beforeSend: function () {
          setCsrf();
        },
        success: (res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Registration deleted...",
            showConfirmButton: false,
            timer: 2000,
          });
          $("#table-training-registration").DataTable().ajax.reload();
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
  });
}

$("#btnUpdateTrainingRegistration").one("click", (event) => {
  let registrationId = $("#registrationId").val();
  event.preventDefault();
  $.ajax({
    method: "PUT",
    url: `/api/trainings/register/${registrationId}`,
    dataType: "JSON",
    contentType: "application/json",
    data: JSON.stringify({
      statusId: $("#statusSelection").val(),
      notes: $("#inputNotes").val(),
    }),
    beforeSend: function () {
      setCsrf();
    },
    success: (res) => {
      showToast("success", "Training registration has been successfully updated").then(
        () => history.back()
      );
    },
    error: (error) => {
      let errorJsn = error.responseJSON;
      console.log(error);

    },
  });
});

function updateStatusInDatabase(registrationId, newStatus, isChecked) {
  $.ajax({
    method: "PUT",
    url: `/api/trainings/register/${registrationId}`,
    dataType: "JSON",
    contentType: "application/json",
    data: JSON.stringify({
      statusId: newStatus,
      isChecked: isChecked,
    }),
    beforeSend: function () {
      setCsrf();
    },
    success: function (res) {
      showToast("success", "Training registration has been successfully updated").then(
        () => {
          $("#table-training-registration").DataTable().ajax.reload();
        }
      );
    },
    error: function (error) {
      let errorJsn = error.responseJSON;
      console.log(error);
    },
  });
}
