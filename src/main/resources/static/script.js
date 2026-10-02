const $ = (id) => document.getElementById(id);
const EXTRA_IDS = ["premiumFood", "bath", "training", "veterinary"];

function fillSelect(select, options) {
    select.replaceChildren(...options.map(option => new Option(option.label, option.id)));
}

function isCustomPackage() {
    return $("carePackage").value === "CUSTOM";
}

function updateExtrasVisibility() {
    const custom = isCustomPackage();
    $("extras").classList.toggle("hidden", !custom);
    $("packageNote").classList.toggle("hidden", custom);
}

function showError(message) {
    $("error").textContent = message || "";
    $("error").classList.toggle("hidden", !message);
}

function renderPlan(plan) {
    $("horseName").textContent = plan.horseName;
    $("resultType").textContent = plan.horseType;
    $("resultPackage").textContent = plan.carePackage;
    $("sessions").textContent = `${plan.weeklySessions} ${plan.weeklySessions === 1 ? "sesión" : "sesiones"} por semana`;
    $("description").textContent = plan.description;
    $("total").textContent = "$" + Number(plan.price).toLocaleString("es-CO");

    $("recommendations").replaceChildren(...plan.recommendations.map(text => {
        const item = document.createElement("li");
        item.textContent = text;
        return item;
    }));
    $("recommendationsBox").classList.toggle("hidden", plan.recommendations.length === 0);

    $("traceCreator").textContent = plan.creator;
    $("traceBaseCare").textContent = plan.baseCare;
    $("traceBuilder").textContent = plan.builder;
    $("traceDecorators").textContent = plan.decorators.length
        ? plan.decorators.join(" → ")
        : "Sin servicios adicionales: se usa solo el cuidado base.";

    $("result").classList.remove("hidden");
    $("result").scrollIntoView({ behavior: "smooth", block: "start" });
}

async function createPlan() {
    const horseName = $("horse").value.trim();
    if (!horseName) {
        showError("Escribe el nombre del caballo.");
        return;
    }
    showError(null);

    const request = {
        horseName,
        horseType: $("horseType").value,
        carePackage: $("carePackage").value
    };
    EXTRA_IDS.forEach(id => {
        request[id] = isCustomPackage() && $(id).checked;
    });

    const button = $("calculate");
    button.disabled = true;
    try {
        const response = await fetch("/api/care-plans", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(request)
        });
        const data = await response.json();
        if (!response.ok) {
            throw new Error(data.error || "No fue posible crear el plan.");
        }
        renderPlan(data);
    } catch (error) {
        showError(error.message);
    } finally {
        button.disabled = false;
    }
}

async function init() {
    try {
        const response = await fetch("/api/options");
        const options = await response.json();
        fillSelect($("horseType"), options.horseTypes);
        fillSelect($("carePackage"), options.carePackages);
        updateExtrasVisibility();
    } catch (error) {
        showError("No se pudo conectar con el servidor.");
    }
    $("carePackage").addEventListener("change", updateExtrasVisibility);
    $("calculate").addEventListener("click", createPlan);
}

init();
