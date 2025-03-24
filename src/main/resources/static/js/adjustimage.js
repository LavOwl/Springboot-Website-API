const profileImage = document.getElementById('imageToEdit');

let isDragging = false;
let startX, startY;
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
    circleSize = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--circle-size'));
   
    imageRect = profileImage.getBoundingClientRect();

    
    offsetX = Math.min(offsetX, imageRect.width/2 - circleSize);
    offsetX = Math.max(offsetX, circleSize - imageRect.width/2);

    offsetY = Math.min(offsetY, imageRect.height/2 - circleSize);
    offsetY = Math.max(offsetY, circleSize - imageRect.height/2);
    

    profileImage.style.transform = `translate(${offsetX}px, ${offsetY}px)`;
  }
});

profileImage.addEventListener('mouseup', function () {
  isDragging = false;
});

profileImage.addEventListener('mouseleave', function () {
  isDragging = false;
});