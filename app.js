const API = "http://localhost:8080";

async function addItem(type) {
  const input = document.getElementById(type + "Input");
  const value = input.value;
  if (!value) return;
  await fetch(API + "/" + type, { method: "POST", body: value });
  input.value = "";
  loadItems(type);
}

async function loadItems(type) {
  const res = await fetch(API + "/" + type);
  const text = await res.text();
  const list = document.getElementById(type + "List");
  list.innerHTML = "";
  text.split(",").filter(x => x).forEach(item => {
    const li = document.createElement("li");
    li.textContent = item;
    list.appendChild(li);
  });
}

["staff","reception","pharmacy","billing"].forEach(loadItems);
