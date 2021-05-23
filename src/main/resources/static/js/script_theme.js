function clear_storage(){
    localStorage.removeItem('theme');
}
function get_storage(){
    const mode = localStorage.getItem('theme');
    alert(mode);
}

const btn1 = document.querySelector(".theme-1");
const btn2 = document.querySelector(".theme-2");
const btn3 = document.querySelector(".theme-3");
const currentTheme = localStorage.getItem("theme");
if (currentTheme == "2") {
    document.body.classList.add("theme2");
}
else if (currentTheme == "3") {
    document.body.classList.add("theme3");
}
else { /*기본테마, 처음 접속시 null에 대비하여...*/
    document.body.classList.add("theme1");
}
const line_btn = document.querySelector(".line-check");
const currentLine = localStorage.getItem("border");

if (currentLine == "1") {
    document.body.classList.add("line1");
}
else{
    document.body.classList.add("line0");
}

btn1.addEventListener("click", function() {
    document.body.classList.remove("theme2");
    document.body.classList.remove("theme3");
    document.body.classList.add("theme1");
    let theme="1"
    localStorage.setItem("theme", theme);
});
btn2.addEventListener("click", function() {
    document.body.classList.remove("theme1");
    document.body.classList.remove("theme3");
    document.body.classList.add("theme2");
    let theme="2"
    localStorage.setItem("theme", theme);
});
btn3.addEventListener("click", function() {
    document.body.classList.remove("theme1");
    document.body.classList.remove("theme2");
    document.body.classList.add("theme3");
    let theme="3"
    localStorage.setItem("theme", theme);
});

line_btn.addEventListener("click", function() {
    document.body.classList.toggle("line1");

    let line = "0";
    if (document.body.classList.contains("line1")) {
        line = "1";
    }
    localStorage.setItem("border", line);
});