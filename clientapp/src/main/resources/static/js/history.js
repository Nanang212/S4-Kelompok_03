$(document).ready(function () {
  const table = $("#table-history").DataTable({
    ajax: {
      method: "GET",
      url: "/api/histories",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        className: "details-control",
        defaultContent: '<ion-icon name="caret-down"></ion-icon>',
      },
      {
        data: null,
        render: function (data, type, row, meta) {
          return meta.row + 1;
        },
      },
      {
        data: null,
        render: (data) => {
          const rootUrl = window.location.origin;
          return `<a href="${rootUrl}/training/${data.trainingRegister.training.id}">${data.trainingRegister.training.title}</a>`;
        },
      },
      {
        data: null,
        render: (data) => {
          return `${data.trainingRegister.trainee.user.username}`;
        },
      },
      {
        data: "status",
        render: function (data) {
          const statusColors = getStatusColors(data.id);
          const textColor = data.id === 2 ? "black" : "white";
          return `<span style="color: ${textColor}; background-color: ${statusColors.bgColor}; border-radius: 8px; padding: 4px;">${data.name}</span>`;
        },
      },
    ],
  });

  table.on("click", "td.details-control", function (e) {
    const tr = e.target.closest("tr");
    const row = table.row(tr);

    if (row.child.isShown()) {
      row.child.hide();
    } else {
      row.child(formatDetailContent(row.data())).show();
    }
  });

  function formatDetailContent(data) {
    const formattedDate = formatDate(data.createdAt);
    return `
      <div>
        <p>Status: ${data.status.name}</p>
        <p>Waktu: ${formattedDate}</p>
      </div>
    `;
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
});
