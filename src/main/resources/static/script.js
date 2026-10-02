document.getElementById("calculate").addEventListener("click", async () => {

    const horse = document.getElementById("horse").value.trim();

    if (!horse) {
        alert("Escribe el nombre del caballo.");
        return;
    }

    const request = {
        premiumFood: document.getElementById("premiumFood").checked,
        bath: document.getElementById("bath").checked,
        training: document.getElementById("training").checked,
        veterinary: document.getElementById("veterinary").checked
    };

    try {
        const response = await fetch("/api/services/calculate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        });

        if (!response.ok) {
            throw new Error("No fue posible calcular el servicio.");
        }

        const data = await response.json();

        document.getElementById("horseName").textContent = horse;
        document.getElementById("description").textContent = data.description;
        document.getElementById("total").textContent =
            "$" + Number(data.price).toLocaleString("es-CO");

        document.getElementById("result").classList.remove("hidden");

    } catch (error) {
        alert(error.message);
    }
});
