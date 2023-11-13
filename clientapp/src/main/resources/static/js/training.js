$(document).ready(function () {
  let trainingSection = document.getElementById("trainingSection");
  let authorities;
  if (trainingSection !== null) {
    authorities = trainingSection.getAttribute("authorities");
    authorities.includes('ADMIN') ? setTrainer() : null;
  }
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
          // Calculate the reverse order for the "No" column
          // return meta.settings._iRecordsTotal - meta.row;
          return meta.row + 1;
        },
      },
      { data: "title" },
      {
        data: null,
        render: (data) => {
          return formatDate(data.startDate);
        },
      },
      {
        data: null,
        render: (data) => {
          return formatDate(data.endDate);
        },
      },
      {
        data: null,
        render: (data) => {
          return data.trainer === null ? "-" : data.trainer.name;
        },
      },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
              <div class="flex justify-start gap-2">
                <!-- Button detail modal -->
                <button
                  type="button"
                  class="btn btn-primary btn-sm"
                  onclick="window.location.href='/training/${data.id}'"
                >
                  <ion-icon name="information-circle" size="large" class="text-blue-500"></ion-icon>
                </button>
                <!-- Button update modal -->
                <button
                  type="button"
                  class="btn btn-warning btn-sm"
                  trainingId="${data.id}"
                  onclick="window.location.href='/training/update/${data.id}'"
                  ${authorities.includes("ADMIN") ? "" : "hidden"}
                >
                  <ion-icon name="create" size="large" class="text-yellow-500"></ion-icon>
                </button>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  trainingId="${data.id}"
                  onclick="deleteTraining(this)"
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


$('#btnSaveTraining').one("click", (event) => {
  event.preventDefault()
  $.ajax({
    method: "POST",
    url: `/api/trainings`,
    dataType: "JSON",
    contentType: "application/json",
    data: JSON.stringify({
      title: $("#inputTitleTraining").val(),
      startDate: $("#inputStartDateTraining").val(),
      endDate: $("#inputEndDateTraining").val(),
      quota: $("#inputQuotaTraining").val(),
      duration: $("#inputDurationTraining").val(),
      address: $("#inputAddressTraining").val(),
      description: $("#inputDescriptionTraining").val(),
      platformUrl: $("#inputUrlTraining").val(),
      trainerId: $("#inputTrainerTraining").val(),
      isOnline: $("#inputLocationTraining").val() === "online",
    }),
    beforeSend : function () {
      setCsrf();
    },
    success: (res) => {
      $("#table-training").DataTable().ajax.reload();
      $("#inputTitleTraining").val('')
      $("#inputStartDateTraining").val('')
      $("#inputEndDateTraining").val('')
      $("#inputQuotaTraining").val('')
      $("#inputDurationTraining").val('')
      $("#inputAddressTraining").val('')
      $("#inputDescriptionTraining").val('')
      $("#inputUrlTraining").val('')
      $("#inputTrainerTraining").val('')
      $("#inputLocationTraining").val('')
      showToast("success", "Training created");

    },
    error: (error) => {
      let errorJsn = error.responseJSON
      showToast("error", errorJsn.message).then(() => location.reload());
    },
  });
})


function setTrainer() {
  $.ajax({
    method: "GET",
    url: `/api/employee?role=trainer`,
    dataType: "JSON",
    contentType: "application/json",
    success: (response) => {
      $.each(response, (index, value) => {
        $(`#inputTrainerTraining`).append(`<option value="${value.id}">${value.name}</option>`)
      })
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function formatDate(inputDate) {
  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    hour12: false
  };
  return new Date(inputDate).toLocaleString("id-ID", options);
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

$("#btnUpdateTraining").on("click", (event) => {
  event.preventDefault();
  let trainingId = $("#updateEmployeeId").val();

  $.ajax({
    method: "PUT",
    url: `/api/trainings/${trainingId}`,
    dataType: "JSON",
    contentType: "application/json",
    data: JSON.stringify({
      title: $("#updateTrainingTitle").val(),
      startDate: $("#updateStartDateTraining").val(),
      endDate: $("#updateEndDateTraining").val(),
      quota: $("#updateQuotaTraining").val(),
      duration: $("#updateDurationTraining").val(),
      address: $("#updateAddressTraining").val(),
      description: $("#updateDescriptionTraining").val(),
      platformUrl: $("#updateUrlTraining").val(),
      trainerId: $("#updateTrainerTraining").val(),
      isOnline: $("#updateLocationTraining").val() === "online",
    }),
    beforeSend: function () {
      // Pastikan CSRF token disertakan
      setCsrf();
    },
    success: (res) => {
      // Tampilkan pemberitahuan sukses
      showToast("success", "Training updated successfully").then(() => history.back());
    },
    error: (error) => {
      let errorJsn = error.responseJSON;
      showToast("error", errorJsn.message).then(() => {
        location.reload();
      });
    },
  });
});

function showTrainingDetail(button) {
  let trainingId = button.getAttribute("trainingId");
  $.ajax({
    method: "GET",
    url: `api/trainings/${trainingId}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#detailTitleTraining").val(res.title);
      $("#detailStartDateTraining").val(res.startDate);
      $("#detailEndDateTraining").val(res.endDate);
      $("#detailQuotaTraining").val(res.quota);
      $("#detailDurationTraining").val(res.duration);
      $("#detailAddressTraining").val(res.address);
      $("#detailUrlTraining").val(res.platformUrl);
      $("#detailLocationTraining").val(res.isOnline ? "online" : "onsite");
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function updateTraining(button) {
  let trainingId = button.getAttribute("trainingId");
  $.ajax({
    method: "GET",
    url: `api/trainings/${trainingId}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#updateTitleTraining").val(res.title);
      $("#updateStartDateTraining").val(res.startDate);
      $("#updateEndDateTraining").val(res.endDate);
      $("#updateQuotaTraining").val(res.quota);
      $("#updateDurationTraining").val(res.duration);
      $("#updateAddressTraining").val(res.address);
      $("#updateUrlTraining").val(res.platformUrl);
      $("#updateLocationTraining").val(res.isOnline ? "online" : "onsite");
      $("#btnUpdateTraining").one("click", (event) => {
        event.preventDefault();
        $.ajax({
          method: "PUT",
          url: `api/trainings/${res.id}`,
          dataType: "JSON",
          contentType: "application/json",
          data: JSON.stringify({
            title: $("#updateTitleTraining").val(),
            startDate: $("#updateStartDateTraining").val(),
            endDate: $("#updateEndDateTraining").val(),
            quota: $("#updateQuotaTraining").val(),
            duration: $("#updateDurationTraining").val(),
            address: $("#updateAddressTraining").val(),
            platformUrl: $("#updateUrlTraining").val(),
            isOnline: $("#updateLocationTraining").val() === "online",
          }),
          beforeSend: function () {
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
            console.log(error);
          },
        });
      });
    },
    error: (error) => {
      console.log(error);
    },
  });
}

function deleteTraining(button) {
  let id = button.getAttribute("trainingId");
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
        url: `api/trainings/${id}`,
        dataType: "JSON",
        contentType: "application/json",
        beforeSend: function () {
          setCsrf();
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
  });
}

function showAddressOrUrlPlatform(type) {
  let isOnline = $('#inputLocationTraining').val() === 'online'
  if (isOnline) {
    $(`#url${type}Wrapper`).attr("hidden", false)
    $(`#address${type}Wrapper`).attr("hidden", true)
  } else {
    $(`#url${type}Wrapper`).attr("hidden", true)
    $(`#address${type}Wrapper`).attr("hidden", false)
  }
}