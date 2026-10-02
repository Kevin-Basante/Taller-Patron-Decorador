const EXTRA_LABELS = {
    PREMIUM_FOOD: "🥕 Premium food",
    BATH: "🛁 Bath and grooming",
    TRAINING: "🏇 Training",
    VETERINARY: "🩺 Veterinary check-up"
};

let templates = [];
let selectedTemplate = null;

function formatPrice(value) {
    return "$" + Number(value).toLocaleString("es-CO");
}

function renderTemplates() {
    const container = document.getElementById("templates");
    container.innerHTML = "";

    templates.forEach(template => {
        const button = document.createElement("button");
        button.type = "button";
        button.className = "template-option";
        if (selectedTemplate && selectedTemplate.templateKey === template.templateKey) {
            button.classList.add("selected");
        }

        const title = document.createElement("strong");
        title.textContent = template.category;
        const price = document.createElement("span");
        price.textContent = formatPrice(template.price);

        button.append(title, price);
        button.addEventListener("click", () => selectTemplate(template));
        container.appendChild(button);
    });
}

function renderExtras() {
    const container = document.getElementById("extras");
    container.innerHTML = "";

    Object.entries(EXTRA_LABELS).forEach(([key, label]) => {
        const row = document.createElement("label");
        row.className = "extra-option";

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.value = key;
        checkbox.checked = selectedTemplate.extras.includes(key);

        const text = document.createElement("span");
        text.textContent = label;

        row.append(checkbox, text);
        container.appendChild(row);
    });
}

function selectTemplate(template) {
    selectedTemplate = template;
    document.getElementById("ageYears").value = template.ageYears;
    document.getElementById("weightKg").value = template.weightKg;
    renderTemplates();
    renderExtras();
}

function renderPlan(containerId, plan) {
    const container = document.getElementById(containerId);
    container.innerHTML = "";

    const addDetail = (label, value) => {
        const paragraph = document.createElement("p");
        paragraph.className = "plan-detail";
        const bold = document.createElement("b");
        bold.textContent = label + ": ";
        paragraph.append(bold, value);
        container.appendChild(paragraph);
    };

    const addList = (label, items) => {
        const block = document.createElement("div");
        block.className = "plan-detail";
        const bold = document.createElement("b");
        bold.textContent = label + ":";
        const list = document.createElement("ul");
        items.forEach(item => {
            const li = document.createElement("li");
            li.textContent = item;
            list.appendChild(li);
        });
        block.append(bold, list);
        container.appendChild(block);
    };

    const price = document.createElement("p");
    price.className = "plan-price";
    price.textContent = formatPrice(plan.price);
    container.appendChild(price);

    addDetail("Horse", plan.horseName);
    addDetail("Age", plan.ageYears + " years");
    addDetail("Weight", plan.weightKg + " kg");
    addDetail("Special care", plan.specialCare);
    addList("Extras", plan.extras.length
        ? plan.extras.map(extra => EXTRA_LABELS[extra] || extra)
        : ["None"]);
    addList("Feeding schedule", plan.feedingSchedule);
    addList("Vaccinations", plan.vaccinations);
}

async function loadTemplates() {
    try {
        const response = await fetch("/api/plans/templates");
        if (!response.ok) {
            throw new Error("Could not load the care plan templates.");
        }
        templates = await response.json();
        selectTemplate(templates[0]);
    } catch (error) {
        alert(error.message);
    }
}

document.getElementById("clonePlan").addEventListener("click", async () => {
    const horseName = document.getElementById("horseName").value.trim();

    if (!selectedTemplate) {
        alert("Choose a template first.");
        return;
    }
    if (!horseName) {
        alert("Write the horse's name.");
        return;
    }

    const extras = Array.from(document.querySelectorAll("#extras input:checked"))
        .map(checkbox => checkbox.value);

    const request = {
        templateKey: selectedTemplate.templateKey,
        horseName: horseName,
        ageYears: Number(document.getElementById("ageYears").value),
        weightKg: Number(document.getElementById("weightKg").value),
        extras: extras
    };

    try {
        const response = await fetch("/api/plans/clone", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(request)
        });
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.error || "Could not clone the care plan.");
        }

        renderPlan("clonedPlan", data.clonedPlan);
        renderPlan("originalTemplate", data.originalTemplate);
        document.getElementById("comparison").classList.remove("hidden");
    } catch (error) {
        alert(error.message);
    }
});

loadTemplates();
