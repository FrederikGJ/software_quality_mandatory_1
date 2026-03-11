const API_BASE = "http://localhost:8080";

async function fetchData(endpoint) {
    const resultDiv = document.getElementById("result");
    resultDiv.classList.remove("hidden");
    resultDiv.innerHTML = '<p class="loading">Henter data...</p>';

    try {
        const response = await fetch(API_BASE + endpoint);
        if (!response.ok) throw new Error("Fejl: " + response.status);
        const data = await response.json();
        renderResult(data);
    } catch (error) {
        resultDiv.innerHTML = '<p class="error">' + error.message + "</p>";
    }
}

async function fetchBulk() {
    const count = document.getElementById("bulkCount").value;
    const num = parseInt(count);
    if (isNaN(num) || num < 2 || num > 100) {
        const resultDiv = document.getElementById("result");
        resultDiv.classList.remove("hidden");
        resultDiv.innerHTML = '<p class="error">Antal skal være mellem 2 og 100</p>';
        return;
    }
    await fetchData("/api/persons/" + num);
}

function renderResult(data) {
    const resultDiv = document.getElementById("result");

    if (Array.isArray(data)) {
        resultDiv.innerHTML = "<h2>Genereret " + data.length + " personer</h2>";
        data.forEach(function (person, i) {
            resultDiv.innerHTML += renderPersonCard(person, i + 1);
        });
    } else {
        resultDiv.innerHTML = "<h2>Resultat</h2>" + renderCard(data);
    }
}

function renderCard(data) {
    let html = '<div class="person-card">';

    if (data.cpr !== undefined) {
        html += renderField("CPR", data.cpr);
    }
    if (data.firstName !== undefined) {
        html += renderField("Fornavn", data.firstName);
    }
    if (data.lastName !== undefined) {
        html += renderField("Efternavn", data.lastName);
    }
    if (data.gender !== undefined) {
        html += renderField("Køn", data.gender === "male" ? "Mand" : "Kvinde");
    }
    if (data.dateOfBirth !== undefined) {
        html += renderField("Fødselsdato", data.dateOfBirth);
    }
    if (data.address !== undefined) {
        html += renderAddress(data.address);
    }
    if (data.phoneNumber !== undefined) {
        html += renderField("Mobilnummer", formatPhone(data.phoneNumber));
    }
    if (data.street !== undefined) {
        html += renderAddress(data);
    }

    html += "</div>";
    return html;
}

function renderPersonCard(person, index) {
    let html = '<div class="person-card">';
    html += '<div class="field"><span class="field-label">Person #' + index + "</span></div>";

    html += renderField("CPR", person.cpr);
    html += renderField("Fornavn", person.firstName);
    html += renderField("Efternavn", person.lastName);
    html += renderField("Køn", person.gender === "male" ? "Mand" : "Kvinde");
    html += renderField("Fødselsdato", person.dateOfBirth);
    html += renderAddress(person.address);
    html += renderField("Mobilnummer", formatPhone(person.phoneNumber));

    html += "</div>";
    return html;
}

function renderAddress(addr) {
    var fullStreet = addr.street + " " + addr.number + ", " + addr.floor + ". " + addr.door;
    var cityLine = addr.postalCode + " " + addr.townName;
    var html = renderField("Adresse", fullStreet);
    html += renderField("", cityLine);
    return html;
}

function renderField(label, value) {
    return (
        '<div class="field">' +
        '<span class="field-label">' + label + "</span>" +
        '<span class="field-value">' + value + "</span>" +
        "</div>"
    );
}

function formatPhone(phone) {
    if (phone.length === 8) {
        return phone.substring(0, 2) + " " + phone.substring(2, 4) + " " +
               phone.substring(4, 6) + " " + phone.substring(6, 8);
    }
    return phone;
}
