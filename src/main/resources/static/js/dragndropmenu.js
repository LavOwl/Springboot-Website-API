const uploadLabel = document.getElementById("uploadLabel");
const uploadInput = document.getElementById("uploadInput");
const uploadData = document.getElementById("uploadData");
const loader = document.getElementById("loader");

uploadInput.addEventListener("change", uploadImage);

async function uploadImage(){
    let file = uploadInput.files[0];
    if (!file) return;
    let imgSource;
    uploadData.style.display = "none";

    if (file.type === "image/heic" || file.name.split('.').pop().toLowerCase() === "heic") {
        try {
            loader.style.display = "block";
            file = await heic2any({ blob: file, toType: "image/png" });
            loader.style.display = "none";
        } catch (e) {
            alert("No se pudo mostrar tu imagen .HEIC, es posible que sea posible publicarla a pesar de esto, de esto fallar intente usar otra extensión (jpg, png).");
            clearImage();
            return;
        }
    }

    imgSource = URL.createObjectURL(file);
    uploadInput.src = imgSource;
    uploadLabel.style.backgroundImage = `url(${imgSource})`;
};

function clearImage(){
    loader.style.display = "none";
    uploadData.style.display = "flex";
    uploadLabel.style.backgroundImage = "none";
    
}

uploadLabel.addEventListener("dragover", function(e){
    e.preventDefault();
});

uploadLabel.addEventListener("drop", function(e){
    e.preventDefault();
    uploadInput.files = e.dataTransfer.files;
    uploadImage();
});