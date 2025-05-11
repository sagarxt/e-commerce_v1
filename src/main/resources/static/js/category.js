function openUpdateCategoryModal(element) {
    // Get data attributes from the clicked element
    const categoryId = element.getAttribute('data-id');
    const categoryName = element.getAttribute('data-name');
    const categoryDescription = element.getAttribute('data-description');
    const categoryImageUrl = element.getAttribute('data-image-url');

    // Set the values of the form fields
    document.getElementById('categoryId').value = categoryId;
    document.getElementById('updateName').value = categoryName;
    document.getElementById('updateDescription').value = categoryDescription;

    // Set the current image
    document.getElementById('currentImage').src = categoryImageUrl; // Ensure the image URL is correct

    // Show the modal
    var modal = new bootstrap.Modal(document.getElementById('updateCategoryModal'));
    modal.show();
}

console.log("Category.js loaded")