$(document).ready(function () {
  let detailCancelTraining = document.getElementById("detailCancelTraining");
  let id = detailCancelTraining.getAttribute("trId");
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
          return `${data.trainee !== null ? data.trainee.user.username : "-"}`;
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
            <div class="flex items-center space-x-8 ">
              <div class="flex items-center flex-col">
              <label for="${checkboxCancelledId}" class="${
            data.status.id === 4 ? "text-green-500" : "text-gray-400"
          }">Accept</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxCancelledId}"
                  ${data.status.id === 4 ? "checked" : ""}
                  onchange="updateStatusCancellation(${data.id}, 4)"
                  ${isStatusCancel ? "disabled" : ""}
                  class="h-5 w-5 rounded border-gray-300 ${
                    data.status.id === 4
                      ? "text-green-600 shadow-sm focus:border-green-300 focus:ring focus:ring-green-200 focus:ring-opacity-50 dark:focus:ring-opacity-40"
                      : "text-gray-400"
                  }"
                >
              </div>
              <div class="flex items-center flex-col">
              <label for="${checkboxPendingId}" class="${
            data.status.id === 5 ? "text-yellow-500" : "text-gray-400"
          }">Request Cancel</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxPendingId}"
                  ${data.status.id === 5 ? "checked" : ""}
                  onchange="updateStatusCancellation(${data.id}, 5)"
                  ${isStatusCancel ? "disabled" : ""}
                  class="h-5 w-5 rounded border-gray-300 ${
                    data.status.id === 5
                      ? "text-yellow-600 shadow-sm focus:border-yellow-300 focus:ring focus:ring-yellow-200 focus:ring-opacity-50 dark:focus:ring-opacity-40"
                      : "text-gray-400"
                  }"
                >
              </div>
              <div class="flex items-center flex-col">
              <label for="${checkboxRejectId}" class="${
            data.status.id === 3 ? "text-red-500" : "text-gray-400"
          }">Reject</label>
                <input
                  type="checkbox"
                  name="statusCheckbox_${data.id}"
                  id="${checkboxRejectId}"
                  ${data.status.id === 3 ? "checked" : ""}
                  onchange="updateStatusCancellation(${data.id}, 3)"
                  ${isStatusCancel ? "disabled" : ""}
                  class="h-5 w-5 rounded border-gray-300 ${
                    data.status.id === 3
                      ? "text-red-600 shadow-sm focus:border-red-300 focus:ring focus:ring-red-200 focus:ring-opacity-50 dark:focus:ring-opacity-40"
                      : "text-gray-400"
                  }"
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
        render: function (data, type, row, meta) {
          return `
            <div class="flex items-center">
              <input
                type="text"
                id="inputNotes_${data.id}"
                class="w-full border border-gray-300 rounded p-2 mr-2"
                placeholder="Add notes..."
                value="${data.notes !== undefined ? data.notes : ""}"
                onchange="saveNotes(${data.id})"
              />
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
                  title = "Delete"
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

function updateStatusCancellation(registrationId, newStatus) {
  let isChecked = $(`#checkbox-${newStatus}-${registrationId}`).prop("checked");

  let updatedData = JSON.parse(sessionStorage.getItem("updatedData")) || [];

  // Cek apakah registrasi ini sudah ditambahkan ke dalam data perubahan
  let existingIndex = updatedData.findIndex(item => item.id === registrationId);

  if (existingIndex !== -1) {
    // Jika sudah ada, update status saja
    updatedData[existingIndex].newStatus = newStatus;
    updatedData[existingIndex].isChecked = isChecked;
  } else {
    // Jika belum ada, tambahkan data perubahan
    updatedData.push({ id: registrationId, newStatus, isChecked });
  }

  sessionStorage.setItem("updatedData", JSON.stringify(updatedData));
}

function saveNotes(id) {
  const inputNotes = document.getElementById(`inputNotes_${id}`);
  const notes = inputNotes.value;

  let updatedNotes = JSON.parse(sessionStorage.getItem("updatedNotes")) || {};
  updatedNotes[id] = notes;
  sessionStorage.setItem("updatedNotes", JSON.stringify(updatedNotes));
}

function submitChangesCancelToDatabase() {
  let loadingModal = Swal.fire({
    html: '<div class="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-transparent"><div class="animate-spin rounded-full border-t-4 border-blue-500 border-solid h-12 w-12"></div></div>',
    showConfirmButton: false,
    allowOutsideClick: false,
    allowEscapeKey: false,
    background: "transparent",
  });

  let updatedData = JSON.parse(sessionStorage.getItem("updatedData")) || [];
  let updatedNotes = JSON.parse(sessionStorage.getItem("updatedNotes")) || {};

  // Ubah semua status yang direject menjadi success
  updatedData.forEach(item => {
    if (item.newStatus === 3) {
      item.newStatus = 1; // Ubah status rejected (3) menjadi success (1)
    }
  });

  // Kirim perubahan status dan catatan ke server
  updateMultipleDataInDatabase(updatedData, updatedNotes, loadingModal);
}

function updateMultipleDataInDatabase(updatedData, updatedNotes, loadingModal) {
  let totalRequests = updatedData.length; // Hitung total permintaan yang akan dikirim
  let completedRequests = 0;

  updatedData.forEach(item => {
    let id = item.id;
    let newStatus = item.newStatus;
    let isChecked = item.isChecked;
    let notes = updatedNotes[id];

    $.ajax({
      method: "PUT",
      url: `/api/trainings/register/${id}`,
      dataType: "JSON",
      contentType: "application/json",
      data: JSON.stringify({
        statusId: newStatus,
        isChecked: isChecked,
        notes: notes,
      }),
      beforeSend: function () {
        setCsrf();
      },
      success: function (res) {
        completedRequests++;

        if (completedRequests === totalRequests) {
          showToast(
            "success",
            "Training registration has been successfully updated"
          ).then(() => {
            $("#detail-training-cancel").DataTable().ajax.reload();
            loadingModal.close();
            sessionStorage.removeItem("updatedData");
            sessionStorage.removeItem("updatedNotes");
          });
        }
      },
      error: function (error) {
        console.error("Error:", error);
        completedRequests++;

        if (completedRequests === totalRequests) {
          loadingModal.close();
        }
      },
    });
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
    success: (response) => {},
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
