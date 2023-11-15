$(document).ready(function () {
  let detailRegisterTraining = document.getElementById(
    "detailRegisterTraining"
  );
  let authorities = detailRegisterTraining.getAttribute("authorities");
  let id = detailRegisterTraining.getAttribute("trId");
  let showStatusColumn = !authorities.includes("TRAINEE");
  $("#detail-training-register").DataTable({
    ajax: {
      method: "GET",
      url: "/api/trainings/register/training/" + id,
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
          return `${data.trainee !== null ? data.trainee.user.username : "-"}`;
        },
      },
      {
        data: null,
        render: function (data, type, row, meta) {
          if (!showStatusColumn) {
            return "";
          }

          let checkboxSuccessId = `checkbox-success-${meta.row}`;
          let checkboxPendingId = `checkbox-pending-${meta.row}`;
          let checkboxRejectId = `checkbox-reject-${meta.row}`;

          let isStatusSuccessOrReject =
            data.status.id === 1 || data.status.id === 3;

          return `
            <div class="flex items-center space-x-8">
              <div class="flex items-center flex-col">
                <label for="${checkboxSuccessId}">Success</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxSuccessId}"
                  ${data.status.id === 1 ? "checked" : ""}
                  onchange="updateStatus(${data.id}, 1)"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                  ${isStatusSuccessOrReject ? "disabled" : ""}
                >
              </div>
              <div class="flex items-center flex-col">
                <label for="${checkboxPendingId}">Pending</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxPendingId}"
                  ${data.status.id === 2 ? "checked" : ""}
                  onchange="updateStatus(${data.id}, 2)"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                  ${isStatusSuccessOrReject ? "disabled" : ""}
                >
              </div>
              <div class="flex items-center flex-col">
                <label for="${checkboxRejectId}">Reject</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxRejectId}"
                  ${data.status.id === 3 ? "checked" : ""}
                  onchange="updateStatus(${data.id}, 3)"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                  ${isStatusSuccessOrReject ? "disabled" : ""}
                >
              </div>
            </div>
          `;
        },
      },
      {
        data: "status.name",
        render: function (data, type, row) {
          let bgColorClass = "";
          let paddingClass = "";
          switch (row.status.id) {
            case 1:
              bgColorClass = "bg-green-400 text-black";
              paddingClass = "px-3 py-1";
              break;
            case 2:
              bgColorClass = "bg-yellow-400 text-black";
              paddingClass = "px-3 py-1";
              break;
            case 3:
            case 4:
              bgColorClass = "bg-red-400 text-black";
              paddingClass = "px-3 py-1";
              break;
            case 5:
              bgColorClass = "bg-blue-400 text-black";
              paddingClass = "px-3 py-1";
              break;
            default:
              bgColorClass = "bg-gray-400 text-black";
              paddingClass = "px-3 py-1";
              break;
          }
          return `<div class="rounded-full inline-block ${bgColorClass} ${paddingClass}">${data}</div>`;
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
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                  title = "Delete-training"
                >
                  <ion-icon name="trash" size="large" class="text-red-500"></ion-icon>
                </button>
              </div>
            `;
        },
      },
    ],
    columnDefs: [
      {
        targets: [2], // Index kolom "Status"
        visible: showStatusColumn,
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

function updateStatusInDatabase(registrationId, newStatus, isChecked) {
  let loadingModal = Swal.fire({
    html: '<div class="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-transparent"><div class="animate-spin rounded-full border-t-4 border-blue-500 border-solid h-12 w-12"></div></div>',
    showConfirmButton: false,
    allowOutsideClick: false,
    allowEscapeKey: false,
    background: "transparent",
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
      showToast(
        "success",
        "Training registration has been successfully updated"
      ).then(() => {
        $("#detail-training-register").DataTable().ajax.reload();
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
      console.log(response);
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
