const profileImage = document.getElementById('imageToEdit');
const imageArea = document.getElementById('imageArea');

let isDragging = false;
let startX = 0;
let startY = 0;
let circleSize = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--circle-size'));

let imageRect = profileImage.getBoundingClientRect();
const imageWidth = imageRect.width;
const imageHeight = imageRect.height;

profileImage.addEventListener('mousedown', function (e) {
  isDragging = true;
  e.preventDefault();
  startX = e.clientX - offsetX;
  startY = e.clientY - offsetY;
});

profileImage.addEventListener('mousemove', function (e) {
  if (isDragging) {
    e.preventDefault();
    offsetX = e.clientX - startX;
    offsetY = e.clientY - startY;
    validateBoundries();
  }
});

function validateBoundries(){
    circleSize = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--circle-size'));
   
    imageRect = profileImage.getBoundingClientRect();

    offsetX = Math.max(Math.min(offsetX, imageRect.width/2 - circleSize), circleSize - imageRect.width/2);
    offsetY = Math.max(Math.min(offsetY, imageRect.height/2 - circleSize), circleSize - imageRect.height/2); 

    profileImage.style.transform = `translate(${offsetX}px, ${offsetY}px)`;
}

profileImage.addEventListener('mouseup', function () {
  isDragging = false;
});

profileImage.addEventListener('mouseleave', function () {
  isDragging = false;
});

imageArea.addEventListener("wheel", (e) => scrollBehaviour(e)); 

function scrollBehaviour(e){
  e.preventDefault();
  const step = 5;
  let newValue = parseInt(sizeInput.value) + (e.deltaY < 0 ? step : -step);
  newValue = Math.max(parseInt(sizeInput.min), Math.min(parseInt(sizeInput.max), newValue));
  sizeInput.value = newValue;
  sizeInput.dispatchEvent(new Event("input")); //Trigger input event manually
};

