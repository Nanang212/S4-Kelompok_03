// All javascript code in this project for now is just for demo DON'T RELY ON IT
$(document).ready(function () {
  let dashboardSection = document.getElementById("dashboardSection");
  let authorities;
  if (dashboardSection !== null) {
    authorities = dashboardSection.getAttribute("authorities");
    authorities.includes('ADMIN') ? setBarChart() : null;
  }
})
const random = (max = 100) => {
  return Math.round(Math.random() * max) + 20;
};

const setBarChart = () => {
  let data;
  $.ajax({
    method: "GET",
    url: `/api/trainings/dashboard`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      new Chart(document.getElementById("barChart"), {
        type: "bar",
        data: {
          labels: months,
          datasets: [
            {
              data: res,
              backgroundColor: colors.primary,
              hoverBackgroundColor: colors.primaryDark,
            },
          ],
        },
        options: {
          scales: {
            yAxes: [
              {
                gridLines: false,
                ticks: {
                  beginAtZero: true,
                  stepSize: 50,
                  fontSize: 12,
                  fontColor: "#97a4af",
                  fontFamily: "Open Sans, sans-serif",
                  padding: 10,
                },
              },
            ],
            xAxes: [
              {
                gridLines: false,
                ticks: {
                  fontSize: 12,
                  fontColor: "#97a4af",
                  fontFamily: "Open Sans, sans-serif",
                  padding: 5,
                },
                categoryPercentage: 0.5,
                maxBarThickness: "10",
              },
            ],
          },
          cornerRadius: 2,
          maintainAspectRatio: false,
          legend: {
            display: false,
          },
        },
      });
    },
    error: (err) => {
      console.log(err);
    },
  });
};

const months = [
  "Jan",
  "Feb",
  "Mar",
  "Apr",
  "May",
  "Jun",
  "Jul",
  "Aug",
  "Sep",
  "Oct",
  "Nov",
  "Dec",
];

const cssColors = (color) => {
  return getComputedStyle(document.documentElement).getPropertyValue(color);
};

const getColor = () => {
  return window.localStorage.getItem("color") ?? "cyan";
};

const colors = {
  primary: cssColors(`--color-${getColor()}`),
  primaryLight: cssColors(`--color-${getColor()}-light`),
  primaryLighter: cssColors(`--color-${getColor()}-lighter`),
  primaryDark: cssColors(`--color-${getColor()}-dark`),
  primaryDarker: cssColors(`--color-${getColor()}-darker`),
};

const doughnutChart = new Chart(document.getElementById("doughnutChart"), {
  type: "doughnut",
  data: {
    labels: ["Oct", "Nov", "Dec"],
    datasets: [
      {
        data: [random(), random(), random()],
        backgroundColor: [
          colors.primary,
          colors.primaryLighter,
          colors.primaryLight,
        ],
        hoverBackgroundColor: colors.primaryDark,
        borderWidth: 0,
        weight: 0.5,
      },
    ],
  },
  options: {
    responsive: true,
    maintainAspectRatio: false,
    legend: {
      position: "bottom",
    },

    title: {
      display: false,
    },
    animation: {
      animateScale: true,
      animateRotate: true,
    },
  },
});

const activeUsersChart = new Chart(
    document.getElementById("activeUsersChart"),
    {
      type: "bar",
      data: {
        labels: [...getData(), ...getData()],
        datasets: [
          {
            data: [...getData(), ...getData()],
            backgroundColor: colors.primary,
            borderWidth: 0,
            categoryPercentage: 1,
          },
        ],
      },
      options: {
        scales: {
          yAxes: [
            {
              display: false,
              gridLines: false,
            },
          ],
          xAxes: [
            {
              display: false,
              gridLines: false,
            },
          ],
          ticks: {
            padding: 10,
          },
        },
        cornerRadius: 2,
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        tooltips: {
          prefix: "Users",
          bodySpacing: 4,
          footerSpacing: 4,
          hasIndicator: true,
          mode: "index",
          intersect: true,
        },
        hover: {
          mode: "nearest",
          intersect: true,
        },
      },
    }
);

const lineChart = new Chart(document.getElementById("lineChart"), {
  type: "line",
  data: {
    labels: months,
    datasets: [
      {
        data: getData(),
        fill: false,
        borderColor: colors.primary,
        borderWidth: 2,
        pointRadius: 0,
        pointHoverRadius: 0,
      },
    ],
  },
  options: {
    responsive: true,
    scales: {
      yAxes: [
        {
          gridLines: false,
          ticks: {
            beginAtZero: false,
            stepSize: 50,
            fontSize: 12,
            fontColor: "#97a4af",
            fontFamily: "Open Sans, sans-serif",
            padding: 20,
          },
        },
      ],
      xAxes: [
        {
          gridLines: false,
        },
      ],
    },
    maintainAspectRatio: false,
    legend: {
      display: false,
    },
    tooltips: {
      hasIndicator: true,
      intersect: false,
    },
  },
});

let randomUserCount = 0;

const usersCount = document.getElementById("usersCount");

const fakeUsersCount = () => {
  randomUserCount = random();
  activeUsersChart.data.datasets[0].data.push(randomUserCount);
  activeUsersChart.data.datasets[0].data.splice(0, 1);
  activeUsersChart.update();
  usersCount.innerText = randomUserCount;
};

setInterval(() => {
  fakeUsersCount();
}, 1000);

function setHeaders() {
  let token = $("meta[name='_csrf']").attr("content");
  let header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
}
