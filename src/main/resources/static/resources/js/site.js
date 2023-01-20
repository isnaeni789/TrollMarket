var moneyConversions = document.querySelectorAll(".moneyConversion");
for (let element of moneyConversions) {
  let convertedValue = Number(element.value).toFixed(2);
  element.value = convertedValue;
}
var numberInputs = document.querySelectorAll("[type=number]");
for (let element of numberInputs) {
  if (element.value == "") {
    element.value = 0;
  }
}
var dateInputs = document.querySelectorAll("[type=date]");
for (let element of dateInputs) {
  if (element.getAttribute("value") != "") {
    let dateValue = new Date(element.getAttribute("value"));
    let formatted = dateValue.toISOString().split("T")[0];
    element.value = formatted;
  }
}

let buyerId = $("#buyer-id").text();
let addBalance = `<button data-target="modal-add-balance" class="js-modal-trigger 
                  button ml-5 is-info">Tambah Dana</button>`;

let role = $(".role").text();
if (role === "[Buyer]") {
  $("#profile-balance").append(addBalance);
  $("#merchandise").removeAttr("href");
  $("#merchandise").css("display", "none");
  $("#shipment").removeAttr("href");
  $("#shipment").css("display", "none");
  $("#admin").removeAttr("href");
  $("#admin").css("display", "none");
  $("#history").removeAttr("href");
  $("#history").css("display", "none");
} else if (role === "[Seller]") {
  $("#shop").removeAttr("href");
  $("#shop").css("display", "none");
  $("#cart").removeAttr("href");
  $("#cart").css("display", "none");
  $("#shipment").removeAttr("href");
  $("#shipment").css("display", "none");
  $("#admin").removeAttr("href");
  $("#admin").css("display", "none");
  $("#history").removeAttr("href");
  $("#history").css("display", "none");
} else if (role === "[Admin]") {
  $("#profile").removeAttr("href");
  $("#profile").css("display", "none");
  $("#shop").removeAttr("href");
  $("#shop").css("display", "none");
  $("#merchandise").removeAttr("href");
  $("#merchandise").css("display", "none");
  $("#cart").removeAttr("href");
  $("#cart").css("display", "none");
}

//Fungsi to open modal from bulma
document.addEventListener("DOMContentLoaded", () => {
  // Functions to open and close a modal
  function openModal($el) {
    $el.classList.add("is-active");
  }

  function closeModal($el) {
    $el.classList.remove("is-active");
  }

  (document.querySelectorAll(".js-modal-trigger") || []).forEach(($trigger) => {
    const modal = $trigger.dataset.target;
    const $target = document.getElementById(modal);

    $trigger.addEventListener("click", () => {
      openModal($target);
    });
  });

  (
    document.querySelectorAll(
      ".modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button"
    ) || []
  ).forEach(($close) => {
    const $target = $close.closest(".modal");

    $close.addEventListener("click", () => {
      closeModal($target);
    });
  });
});

const formatterIDR = new Intl.NumberFormat("id-ID", {
  style: "currency",
  currency: "IDR",
});

const SECRET_KEY = "liberate-tutume-ex-inferis-ad-astra-per-aspera";
const AUDIENCE = "TrollMarketWebUI";
const token = localStorage.getItem("token");

$(".login").click(function () {
  let login = {
    username: $("#username").val(),
    password: $("#password").val(),
    subject: "Trial",
    secretKey: SECRET_KEY,
    audience: AUDIENCE,
  };

  $.ajax({
    method: "POST",
    url: "http://localhost:8020/api/authenticate",
    data: JSON.stringify(login),
    contentType: "application/json",
    success: function (response) {
      localStorage.setItem("token", response.token);
    },
  });
});

function addNewBuyerBalance() {
  let dto = {
    id: buyerId,
    balance: $("#balance-value").val(),
  };

  $.ajax({
    method: "PATCH",
    url: "http://localhost:8020/api/profile/topup-buyer",
    headers: { Authorization: "Bearer " + token },
    data: JSON.stringify(dto),
    contentType: "application/json",
    success: function (result) {
      console.log(result);
      location.reload();
    },
    error: function (e) {
      alert("Error!");
      console.log("ERROR: ", e);
    },
  });
}

function getProductShopInfo(id) {
  $.ajax({
    method: "GET",
    url: "http://localhost:8020/api/shop/product/" + id,
    headers: { Authorization: "Bearer " + token },
    success: function (response) {
      $("#product-info-name").val(response.name);
      $("#product-info-category").val(response.categoryName);
      $("#product-info-description").val(response.description);
      $("#product-info-price").val(formatterIDR.format(response.price));
      $("#product-info-seller").val(response.sellerName);
    },
  });
}

function getProductMerchandiseInfo(id) {
  $.ajax({
    method: "GET",
    url: "http://localhost:8020/api/merchandise/product/" + id,
    headers: { Authorization: "Bearer " + token },
    success: function (response) {
      $("#product-merchandise-name").val(response.name);
      $("#product-merchandise-category").val(response.categoryName);
      $("#product-merchandise-description").val(response.description);
      $("#product-merchandise-price").val(formatterIDR.format(response.price));
      $("#product-merchandise-discontinue").val(response.discontinue);
    },
  });
}

function getProductId(id) {
  console.log(id);
  $("#cart-product-id").val(id);
}

function upsertCart() {
  let dto = {
    id: "",
    buyerId: $("#cart-buyer-id").val(),
    productId: $("#cart-product-id").val(),
    shipperId: $("#cart-shipment").val(),
    quantity: $("#cart-quantity").val(),
  };
  $.ajax({
    method: "POST",
    url: "http://localhost:8020/api/cart",
    headers: { Authorization: "Bearer " + token },
    data: JSON.stringify(dto),
    contentType: "application/json",
    success: function (result) {
      location.reload();
    },
    error: function (e) {
      alert("Error!");
      console.log("ERROR: ", e);
    },
  });
}

function getUpdateShipment(id) {
  $.ajax({
    method: "GET",
    url: "http://localhost:8020/api/shipment/" + id,
    headers: { Authorization: "Bearer " + token },
    success: function (response) {
      $("#shipment-id").val(response.id);
      $("#shipment-name").val(response.name);
      $("#shipment-price").val(response.price);
      $("#shipment-is-active").prop("checked", response.isActive);
    },
  });
}

function addNewShipment() {
  $("#shipment-id").val("");
  $("#shipment-name").val("");
  $("#shipment-price").val("");
  $("#shipment-is-active").prop("checked", false);
}

function upsertShipment() {
  let dto = {
    id: $("#shipment-id").val(),
    name: $("#shipment-name").val(),
    price: $("#shipment-price").val(),
    isActive: $("#shipment-is-active").is(":checked"),
  };

  if (dto.id == "") {
    $.ajax({
      method: "POST",
      url: "http://localhost:8020/api/shipment",
      headers: { Authorization: "Bearer " + token },
      data: JSON.stringify(dto),
      contentType: "application/json",
      success: function (result) {
        console.log(result);
        location.reload();
      },
      error: function (e) {
        alert("Error!");
        console.log("ERROR: ", e);
      },
    });
  } else {
    $.ajax({
      type: "PUT",
      url: "http://localhost:8020/api/shipment",
      headers: { Authorization: "Bearer " + token },
      data: JSON.stringify(dto),
      contentType: "application/json",
      success: function (result) {
        console.log(result);
        location.reload();
      },
      error: function (e) {
        alert("Error!");
        console.log("ERROR: ", e);
      },
    });
  }
}
