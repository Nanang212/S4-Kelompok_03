$(document).ready(function () {
  $("#table-survey").DataTable({
    ajax: {
      method: "GET",
      url: "/api/survey",
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
          return `
                <a
                  href="http://localhost:9090/training/${data.trainingRegister.training.id}"
                  class="btn btn-warning btn-sm"
                  title = "Update-Employee"
                >${data.trainingRegister.training.title}</a>
          `
        }
      },
      {
        data: null,
        render: (data) => {
          return formatDate(data.trainingRegister.training.startDate);
        },
      },
      {
        data: null,
        render: (data) => {
          return formatDate(data.trainingRegister.training.endDate);
        },
      },
      { data: "resource" },
      { data: "trainerCompotence" },
      { data: "learningQuality" },
    ],
  });
});

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