const url = "/email/registration";

let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

async function restTemplate(url, method, requestData) {
  const options = {
    method: method,
    headers: {
      "Content-Type": "application/json",
      "X-CSRF-TOKEN": token,
    },
  };

  if (method !== "GET" && requestData) {
    (options.dataType = "JSON"), (options.body = JSON.stringify(requestData));
  }

  try {
    const response = await fetch(url, options);
    if (!response.ok)
      throw new Error(
        `Gagal Ambil Data : ${response.status} - ${response.statusText}`
      );
    return await response.json();
  } catch (err) {
    throw new Error(`Terjadi Error di : ${err.message}`);
  }
}

$("#submitRegister").click((event) => {
  event.preventDefault();
console.log("iso");
  let name = $("#name").val();
  let email = $("#email").val();
console.log(name);
  let data = {
    name: name,
    email: email,
  };

 alert("please cek your email to verify your account")

  create(data)

  $("#name").val("");
  $("#email").val("");
});

async function create(requestData) {
  return restTemplate(`${url}`, "POST", requestData);
}