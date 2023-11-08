$(document).ready(function () {
  $("#table-history").DataTable({
    ajax: {
      method: "GET",
      url: "/api/histories",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: function (data, type, row, meta) {
          return meta.row + 1;
        },
      },
      {
        data: null,
        render: (data) => {
          console.log(data)
          return data.trainingRegister.training.title;
        }
      },
      {
        data: null,
        render: (data) => {
          return formatDate(data.createdAt);
        }
      },
      { data: "status.name" },
    ],
  });
});

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