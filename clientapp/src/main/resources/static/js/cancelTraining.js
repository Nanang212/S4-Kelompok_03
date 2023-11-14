$(document).ready(function () {
  $("#table-training-cancellation").DataTable({
    ajax: {
      method: "GET",
      url: "/api/trainings/register/cancel",
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
      // {
      //   data: null,
      //   render: function (data, type, row, meta) {
      //     if (!showStatusColumn) {
      //       return "";
      //     }

      //     let checkboxSuccessId = `checkbox-success-${meta.row}`;
      //     let checkboxPendingId = `checkbox-pending-${meta.row}`;
      //     let checkboxRejectId = `checkbox-reject-${meta.row}`;

      //     let isStatusSuccess = data.currentStatus.id === 1;

      //     return `
      //     <div class="flex items-center justify-center space-x-4">
      //       <div>
      //         <label for="${checkboxSuccessId}">Success</label>
      //         <input
      //           type="checkbox"
      //           name="statusCheckbox_${data.id}"
      //           id="${checkboxSuccessId}"
      //           ${data.currentStatus.id === 1 ? "checked" : ""}
      //           onchange="updateStatus(${data.id}, 1)"
      //           ${authorities.includes("ADMIN") ? "" : "hidden"}
      //           ${isStatusSuccess ? "disabled" : ""}
      //         >
      //       </div>
      //       <div>
      //         <label for="${checkboxPendingId}">Pending</label>
      //         <input
      //           type="checkbox"
      //           name="statusCheckbox_${data.id}"
      //           id="${checkboxPendingId}"
      //           ${data.currentStatus.id === 2 ? "checked" : ""}
      //           onchange="updateStatus(${data.id}, 2)"
      //           ${authorities.includes("ADMIN") ? "" : "hidden"}
      //           ${isStatusSuccess ? "disabled" : ""}
      //         >
      //       </div>
      //       <div>
      //         <label for="${checkboxRejectId}">Reject</label>
      //         <input
      //           type="checkbox"
      //           name="statusCheckbox_${data.id}"
      //           id="${checkboxRejectId}"
      //           ${data.currentStatus.id === 3 ? "checked" : ""}
      //           onchange="updateStatus(${data.id}, 3)"
      //           ${authorities.includes("ADMIN") ? "" : "hidden"}
      //           ${isStatusSuccess ? "disabled" : ""}
      //         >
      //       </div>
      //     </div>
      //   `;
      //   },
      // },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
              <div class="flex justify-start gap-3">
                <button
                type="button"
                class="btn btn-primary btn-sm"
                onclick="window.location.href='/training/cancel/detail/${data.training.id}'"
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