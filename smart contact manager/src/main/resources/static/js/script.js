console.log("JS is working");

const toggleSidebar = () => {
    // Check if sidebar is visible
    if ($('.sidebar').is(":visible")) {
        // Hide sidebar
        $(".sidebar").hide();
        // Reset content margin
        $(".content").css("margin-left", "0%");
    } else {
        // Show sidebar
        $(".sidebar").show();
        // Adjust content margin
        $(".content").css("margin-left", "20%");
    }
};



// Make sure DOM is fully loaded before initializing TinyMCE
document.addEventListener('DOMContentLoaded', function () {
    tinymce.init({
        selector: '#description',
        plugins: 'advlist autolink lists link image charmap print preview hr anchor pagebreak code',
        toolbar: 'undo redo | formatselect | fontsizeselect | bold italic underline | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help',
        fontsize_formats: '8pt 10pt 12pt 14pt 18pt 24pt 36pt',
        toolbar_mode: 'floating',
    });
});
