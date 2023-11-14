$(document).ready(function () {
  let detailCancelTraining = document.getElementById("detailCancelTraining");
  let id = detailCancelTraining.getAttribute("trId")
  $("#detail-training-cancel").DataTable({
    ajax: {
      method: "GET",
      url: "/api/trainings/register/cancel/" + id,
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
      {
        data: null,
        render: (data) => {
          return `${data.trainee !== null ? data.trainee.user.username :'-' }`;
        },
      },
      {
        data: null,
        render: function (data, type, row, meta) {
          let checkboxCancelledId = `checkbox-4-${data.id}`;
          let checkboxPendingId = `checkbox-5-${data.id}`;
          let checkboxRejectId = `checkbox-3-${data.id}`;

          let isStatusCancel = data.status.id === 4;

          return `
            <div class="flex items-center justify-center space-x-4">
              <div>
                <label for="${checkboxCancelledId}">Cancelled</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxCancelledId}"
                  ${data.status.id === 4 ? "checked" : ""}
                  onchange="updateStatusCancellation(${data.id}, 4)"
                  ${isStatusCancel ? "disabled" : ""}
                >
              </div>
              <div>
                <label for="${checkboxPendingId}">Request Cancel</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxPendingId}"
                  ${data.status.id === 5 ? "checked" : ""}
                  onchange="updateStatusCancellation(${data.id}, 5)"
                  ${isStatusCancel ? "disabled" : ""}
                >
              </div>
              <div>
                <label for="${checkboxRejectId}">Reject</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxRejectId}"
                  ${data.status.id === 3 ? "checked" : ""}
                  onchange="updateStatusCancellation(${data.id}, 3)"
                  ${isStatusCancel ? "disabled" : ""}
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
              <div class="flex justify-start gap-3">
                <button
                    type="button"
                    class="btn btn-warning btn-sm"
                    registrationId="${data.id}"
                    onclick="downloadAttachment(this)"
                    title="Download attachment"
                  >
                  <ion-icon name="download" size="large" class="text-green-500"></ion-icon>
                </button>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  registrationId="${data.id}"
                  onclick="deleteTrainingRegistration(this)"
                >
                  <ion-icon name="trash" size="large" class="text-red-500"></ion-icon>
                </button>
              </div>
            `;
        },
      }
    ]
  });
});

function updateStatusCancellation(registrationId, newStatus) {
  // Dapatkan nilai isChecked
  let isChecked = $(`#checkbox-${newStatus}-${registrationId}`).prop("checked");
  // Panggil fungsi untuk mengirim permintaan Ajax untuk memperbarui status di database
  updateStatusCancellationInDatabase(registrationId, newStatus, isChecked);
}

function updateStatusCancellationInDatabase(registrationId, newStatus, isChecked) {
  let loadingModal = Swal.fire({
    html: '<div class="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-transparent"><div class="animate-spin rounded-full border-t-4 border-blue-500 border-solid h-12 w-12"></div></div>',
    showConfirmButton: false,
    allowOutsideClick: false,
    allowEscapeKey: false,
    background: 'transparent',
  });

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
      showToast("success", "Training registration has been successfully updated").then(() => {
        $("#detail-training-cancel").DataTable().ajax.reload();
        if (res.currentStatus.id === 3) {
          updateStatusCancellationInDatabase(res.id, 1, true)
        }
        loadingModal.close();
      });
    },
    error: function (error) {
      loadingModal.close();

      let errorJsn = error.responseJSON;
      console.log(error);
    },
  });
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

function downloadAttachment(button) {
  let id = button.getAttribute("registrationId");
  $.ajax({
    method: "GET",
    url: `/api/trainings/register/attachment/${id}`,
    contentType: "application/pdf",
    success: (response) => {

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
          $("#detail-training-register").DataTable().ajax.reload();
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