$(document).ready(function () {
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
        }
      },
      { data: "currentStatus.name" },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
              <div class="flex justify-center gap-3">
                <!-- Button update modal -->
                <button
                  type="button"
                  class="btn btn-warning btn-sm"
                  data-modal-target="updateTrainingRegistrationModal"
                  data-modal-toggle="updateTrainingRegistrationModal"
                  registrationId="${data.id}"
                  onclick="updateTrainingRegister(this)"
                >
                  <ion-icon name="create" size="large"></ion-icon>
                </button>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  registrationId="${data.id}"
                  onclick="deleteTrainingRegistration(this)"
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

function setStatus() {
  $.ajax({
    method: "GET",
    url: `/api/status`,
    dataType: "JSON",
    contentType: "application/json",
    success: (response) => {
      $.each(response, (index, value) => {
        $(`#statusSelection`).append(`<option value="${value.id}">${value.name}</option>`)
      })
    },
    error: (err) => {
      console.log(err)
    },
  });
}

setStatus()

function updateTrainingRegister(button) {
  let registrationId = button.getAttribute("registrationId")
  $.ajax({
    method: "GET",
    url: `/api/trainings/register/${registrationId}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $('#statusSelection').val(res.currentStatus.id)
      $('#btnUpdateTrainingRegistration').one("click", (event) => {
        event.preventDefault()
        $.ajax({
          method: "PUT",
          url: `/api/trainings/register/${res.id}`,
          dataType: "JSON",
          contentType: "application/json",
          data: JSON.stringify({
            status: $('#statusSelection').val()
          }),
          beforeSend : function () {
            setCsrf();
          },
          success: (res) => {
            $("#updateTrainingRegistrationModal").hide();
            $("#table-training-registration").DataTable().ajax.reload();
            Swal.fire({
              position: "center",
              icon: "success",
              title: "Registration status updated...",
              showConfirmButton: false,
              timer: 2000,
            });
          },
          error: (error) => {
            console.log(error)
          },
        });
      })
    },
    error: (error) => {
      console.log(error)
    },
  });
}

function deleteTrainingRegistration(button) {
  let id = button.getAttribute('registrationId')
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
        url: `/api/trainings/register/${id}`,
        dataType: "JSON",
        contentType: "application/json",
        beforeSend: function () {
          setCsrf()
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
  })
}