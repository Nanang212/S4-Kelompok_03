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
          return `
            <a href="http://localhost:9090/training/${data.trainingRegister.training.id}">${data.trainingRegister.training.title}</a>
          `;
        },
      },
      {
        data: null,
        render: (data) => {
          return `
            <a href="http://localhost:9090/profile">${data.trainingRegister.trainee.user.username}</a>
          `;
        },
      },
      {
        data: null,
        render: (data) => {
          return formatDate(data.createdAt);
        },
      },
      {
        data: "status",
        render: function (data) {
          let statusColors = getStatusColors(data.id);
          let textColor = data.id === 2 ? "black" : "white";
          return `<span style="color: ${textColor}; background-color: ${statusColors.bgColor}; border-radius: 8px; padding: 4px;">${data.name}</span>`;
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

function formatDate(inputDate) {
  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    hour12: false,
    timeZone: "GMT",
  };
  return new Date(inputDate).toLocaleString("id-ID", options);
}
