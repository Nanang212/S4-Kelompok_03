$(document).ready(function () {
    $("#table-region").DataTable({
      ajax: {
        method: "GET",
        url: "api/region",
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
        { data: "name" },
        {
          data: null,
          render: (data) => {
            return `
              <div class="flex justify-center gap-2">
                <!-- Button detail modal -->
                <button
                  type="button"
                  class="btn btn-primary btn-sm"
                  data-bs-toggle="modal"
                  data-bs-target="#detail"
                  data-id="${data.id}"
                  data-name="${data.name}"
                  onclick="showRegionDetails(this)"
                  title="Detail ${data.name}"
                >
                  <ion-icon name="information-circle" size="large" class="text-blue-500"></ion-icon>
                </button>
                <!-- Button update modal -->
                <button
                  type="button"
                  class="btn btn-warning btn-sm"
                  data-bs-toggle="modal"
                  data-bs-target="#updateRegionModal"
                  data-id="${data.id}"
                  data-name="${data.name}"
                  title="Update ${data.name}"
                  onclick="openUpdateModal(this)"
                >
                  <ion-icon name="create" size="large" class="text-yellow-500"></ion-icon>
                </button>
                <!-- Button delete modal -->
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  title="Delete ${data.name}"
                  regionId="${data.id}"
                  regionName="${data.name}"
                  onclick="confirmDelete(this)"
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