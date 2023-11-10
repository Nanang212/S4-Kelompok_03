$(document).ready(function () {
  let trainingRegisterSection = document.getElementById(
    "trainingRegisterSection"
  );
  let authorities;
  if (trainingRegisterSection !== null) {
    authorities = trainingRegisterSection.getAttribute("authorities");
  }
  $("#table-training-registration").DataTable({
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
        data: "currentStatus",
        render: function (data, type, row, meta) {
          let statusColors = getStatusColors(data.id);
          let textColor = data.id === 2 ? "black" : "white";
          return `<span style="color: ${textColor}; background-color: ${statusColors.bgColor}; border-radius: 8px; padding: 4px;">${data.name}</span>`;
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
                  <ion-icon name="download" size="large" class="text-blue-500"></ion-icon>
                </button>
                <!-- Button update modal -->
                <button
                  type="button"
                  class="btn btn-warning btn-sm"
                  data-modal-target="updateTrainingRegistrationModal"
                  data-modal-toggle="updateTrainingRegistrationModal"
                  registrationId="${data.id}"
                  onclick="window.location.href='/training/register/update/${
                    data.id
                  }'"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                >
                  <ion-icon name="create" size="large" class="text-yellow-500"></ion-icon>
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

setStatus();

// function updateTrainingRegister(button) {
//   let registrationId = button.getAttribute("registrationId");
//   $.ajax({
//     method: "GET",
//     url: `/api/trainings/register/${registrationId}`,
//     dataType: "JSON",
//     contentType: "application/json",
//     success: (res) => {
//       $("#inputNotes").val(res.notes);
//       $("#statusSelection").val(res.currentStatus.id);
//       $("#btnUpdateTrainingRegistration").one("click", (event) => {
//         event.preventDefault();
//         $.ajax({
//           method: "PUT",
//           url: `/api/trainings/register/${res.id}`,
//           dataType: "JSON",
//           contentType: "application/json",
//           data: JSON.stringify({
//             statusId: $("#statusSelection").val(),
//             notes: $("#inputNotes").val(),
//           }),
//           beforeSend: function () {
//             setCsrf();
//           },
//           success: (res) => {
//             $("#updateTrainingRegistrationModal").hide();
//             $("#table-training-registration").DataTable().ajax.reload();
//             Swal.fire({
//               position: "center",
//               icon: "success",
//               title: "Registration status updated...",
//               showConfirmButton: false,
//               timer: 2000,
//             });
//           },
//           error: (error) => {
//             console.log(error);
//           },
//         });
//       });
//     },
//     error: (error) => {
//       console.log(error);
//     },
//   });
// }

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
      showToast("success", "Pendaftaran pelatihan berhasil diperbarui").then(
        () => history.back()
      );
    },
    error: (error) => {
      let errorJsn = error.responseJSON;
      console.log(error);

      // showToast("error", errorJsn.message).then(() => {
      //   location.reload();
      // });
    },
  });
});
