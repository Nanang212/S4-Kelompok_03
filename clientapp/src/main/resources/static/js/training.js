$(document).ready(function () {
  $("#table-training").DataTable({
    ajax: {
      method: "GET",
      url: "api/trainings",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: function (data, type, row, meta) {
          return meta.row + 1;
        },
      },
      { data: "title" },
      {
        data: null,
        render: (data) => {
          return formatDate(data.startDate)
        }
      },
      {
        data: null,
        render: (data) => {
          return formatDate(data.endDate)
        }
      },
      {
        data: null,
        render: (data) => {
          return data.trainer === null ? '-' : data.trainer.name;
        }
      },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
              <div class="flex justify-center gap-3">
                <!-- Button detail modal -->
                <button
                  type="button"
                  class="btn btn-primary btn-sm"
                  data-modal-target="detailTrainingModal"
                  data-modal-toggle="detailTrainingModal"
                  trainingId="${data.id}"
                  onclick="showTrainingDetail(this)"
                >
                  <ion-icon name="information-circle" size="large"></ion-icon>
                </button>
                <!-- Button update modal -->
                <button
                  type="button"
                  class="btn btn-warning btn-sm"
                  data-modal-target="updateTrainingModal"
                  data-modal-toggle="updateTrainingModal"
                  trainingId="${data.id}"
                  onclick="updateTraining(this)"
                >
                  <ion-icon name="create" size="large"></ion-icon>
                </button>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  trainingId="${data.id}"
                  onclick="deleteTraining(this)"
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

function setRoles(type) {
  $.ajax({
    method: "GET",
    url: `api/roles`,
    dataType: "JSON",
    contentType: "application/json",
    success: (response) => {
      $.each(response, (index, value) => {
        $(`#role${type}Selection`).append(`<option value="${value.id}">${value.name}</option>`)
      })
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function formatDate(inputDate) {
  const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    hour12: false,
    timeZone: 'GMT'
  }
  return new Date(inputDate).toLocaleString('id-ID', options);
}

$('#btnSaveTraining').one('click', (event) => {
  event.preventDefault()
  let title = $('#inputTitleTraining').val()
  let startDate = $('#inputStartDateTraining').val()
  let endDate = $('#inputEndDateTraining').val()
  let quota = $('#inputQuotaTraining').val()
  let duration = $('#inputDurationTraining').val()
  let address = $('#inputAddressTraining').val()
  let url = $('#inputUrlTraining').val()
  let isOnline = $('#inputLocationTraining').val() === 'online';
  if (title === "" || title === null) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Please fill out Training title!!!",
    });
  } else if (startDate === "" || startDate === null) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Please fill out start date!!!",
    });
  } else if (endDate === "" || endDate === null) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Please fill out start date!!!",
    });
  } else if (quota === "" || quota === null) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Please fill out quota !!!",
    })
  } else {
    $.ajax({
      method: "POST",
      url: "api/trainings",
      dataType: "JSON",
      contentType: "application/json",
      data: JSON.stringify({
        title: title,
        startDate: startDate,
        endDate: endDate,
        quota: quota,
        duration: duration,
        address: address,
        platformUrl: url,
        isOnline: isOnline,
      }),
      beforeSend: function () {
        setCsrf()
      },
      success: (res) => {
        // $("#addTrainingModal").modal("hide");
        $("#table-training").DataTable().ajax.reload();
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Training successfully created ...",
          showConfirmButton: false,
          timer: 2000,
        });
        $('#inputTitleTraining').val('')
        $('#inputStartDateTraining').val('')
        $('#inputEndDateTraining').val('')
        $('#inputQuotaTraining').val('')
        $('#inputDurationTraining').val('')
        $('#inputAddressTraining').val('')
        $('#inputUrlTraining').val('')
        $('#inputLocationTraining').val('')
      },
      error: (err) => {
        console.log(err.responseJSON)
      },
    });
  }
})

function showTrainingDetail(button) {
  let trainingId = button.getAttribute("trainingId")
  $.ajax({
    method: "GET",
    url: `api/trainings/${trainingId}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $('#detailTitleTraining').val(res.title)
      $('#detailStartDateTraining').val(res.startDate)
      $('#detailEndDateTraining').val(res.endDate)
      $('#detailQuotaTraining').val(res.quota)
      $('#detailDurationTraining').val(res.duration)
      $('#detailAddressTraining').val(res.address)
      $('#detailUrlTraining').val(res.platformUrl)
      $('#detailLocationTraining').val(res.isOnline ? 'online' : 'onsite')
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function updateTraining(button) {
  let trainingId = button.getAttribute("trainingId")
  $.ajax({
    method: "GET",
    url: `api/trainings/${trainingId}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $('#updateTitleTraining').val(res.title)
      $('#updateStartDateTraining').val(res.startDate)
      $('#updateEndDateTraining').val(res.endDate)
      $('#updateQuotaTraining').val(res.quota)
      $('#updateDurationTraining').val(res.duration)
      $('#updateAddressTraining').val(res.address)
      $('#updateUrlTraining').val(res.platformUrl)
      $('#updateLocationTraining').val(res.isOnline ? 'online' : 'onsite');
      $('#btnUpdateTraining').one("click", (event) => {
        event.preventDefault()
        $.ajax({
          method: "PUT",
          url: `api/trainings/${res.id}`,
          dataType: "JSON",
          contentType: "application/json",
          data: JSON.stringify({
            title: $('#updateTitleTraining').val(),
            startDate: $('#updateStartDateTraining').val(),
            endDate: $('#updateEndDateTraining').val(),
            quota: $('#updateQuotaTraining').val(),
            duration: $('#updateDurationTraining').val(),
            address: $('#updateAddressTraining').val(),
            platformUrl: $('#updateUrlTraining').val(),
            isOnline: $('#updateLocationTraining').val() === 'online'
          }),
          beforeSend : function () {
            setCsrf();
          },
          success: (res) => {
            $("#updateTrainingModal").hide();
            $("#table-training").DataTable().ajax.reload();
            Swal.fire({
              position: "center",
              icon: "success",
              title: "Training successfully updated...",
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

function deleteTraining(button) {
  let id = button.getAttribute('trainingId')
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
        url: `api/trainings/${id}`,
        dataType: "JSON",
        contentType: "application/json",
        beforeSend: function () {
          setCsrf()
        },
        success: (res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Training successfully deleted...",
            showConfirmButton: false,
            timer: 2000,
          });
          $("#table-training").DataTable().ajax.reload();
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