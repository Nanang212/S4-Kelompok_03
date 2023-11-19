$(document).ready(function () {
  let trainingRegisterSection = document.getElementById(
    "trainingRegisterSection"
  );
  let authorities;
  if (trainingRegisterSection !== null) {
    authorities = trainingRegisterSection.getAttribute("authorities");
  }
  let showStatusColumn = !authorities.includes("TRAINEE");

  let dataTable = $("#table-training-registration").DataTable({
    ajax: {
      method: "GET",
      url: "/api/trainings/register/training",
      dataSrc: "",
      data: null,//mencoba menambahkan urutan tapi masih belum berhasil
      // data: { order: [["currentStatus.id", "desc"]] },//mencoba menambahkan urutan tapi masih belum berhasil
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
          return `${data.trainer !== null ? data.trainer.user.username :'-' }`;
        },
      },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
              <div class="flex justify-start gap-3">
              <!-- Button cancel -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  trainingId="${data.training.id}"
                  onclick="cancelTrainingRegistration(this)"
                  ${authorities.includes("TRAINEE") ? "" : "hidden"}
                  title = "cancel-training"
                >
                  <ion-icon name="arrow-undo-circle" size="large" class="text-red-500"></ion-icon>
                </button>
                <button
                type="button"
                class="btn btn-primary btn-sm"
                onclick="window.location.href='/training/register/detail/${data.training.id}'"
                title = "detail-training"
                >
                  <ion-icon name="information-circle" size="large" class="text-blue-500"></ion-icon>
                </button>
              </div>
            `;
        },
      },
    ]
  });
});

function cancelTrainingRegistration(button) {
  let id = button.getAttribute("trainingId");

  Swal.fire({
    title: 'Are you sure want to cancel training?',
    html: `
      <input
        id="swal-input1"
        class="swal2-input"
        placeholder="Add notes (required)"
        required
      >
    `,
    showCancelButton: true,
    confirmButtonText: 'Yes, cancel!',
    preConfirm: () => {
      const notes = Swal.getPopup().querySelector('#swal-input1').value;
      if (!notes) {
        Swal.showValidationMessage('Please enter notes');
      }
      else {
        return $.ajax({
          method: 'POST',
          url: `/api/trainings/register/cancel`,
          dataType: 'JSON',
          contentType: 'application/json',
          data: JSON.stringify({
            trainingId: id,
            notes: notes
          }),
          beforeSend: function () {
            setCsrf();
          },
          success: function (res) {
            // Update status.notes in local data if successful
            let table = $("#table-training-registration").DataTable();
            let data = table.row($(button).parents('tr')).data();
            if (data) {
              table.row($(button).parents('tr')).data(data).draw();
            }
            $("#table-training-registration").DataTable().ajax.reload();
            Swal.fire({
              icon: 'success',
              title: 'Registration request to cancel...',
              showConfirmButton: false,
              timer: 2000
            }).then(() => {
              showToast('success', 'Successfully canceled the training.');
            });
          },
          error: function (err) {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Something went wrong!',
            });
          }
        });
      }
    },
    allowOutsideClick: () => !Swal.isLoading()
  });
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
      showToast(
        "success",
        "Training registration has been successfully updated"
      ).then(() => history.back());
    },
    error: (error) => {
      let errorJsn = error.responseJSON;
      console.log(error);
    },
  });
});



