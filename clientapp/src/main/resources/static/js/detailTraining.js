$(document).ready(function () {
  let detailRegisterTraining = document.getElementById("detailRegisterTraining");
  let id = detailRegisterTraining.getAttribute("trId")
  console.log(id);
    $("#detail-training-register").DataTable({
      ajax: {
        method: "GET",
        url: "/api/trainings/register/training/" + id,
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
            // return `${}`;
          },
        },
      ],
    });
  });
