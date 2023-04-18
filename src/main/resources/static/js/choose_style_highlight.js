var style = document.getElementById('style_default');
var path = "/static/js/highlight/styles/";

$('#style_def').click(function () {
    style.setAttribute('href', path+'default.min.css');
});
$('#style_dark').click(function () {
    style.setAttribute('href', path+'a11y-dark.min.css');
});
$('#style_light').click(function () {
    style.setAttribute('href', path+'a11y-light.min.css');
});
$('#style_atom_one_dark').click(function () {
    style.setAttribute('href', path+'atom-one-dark.min.css');
});
$('#style_docco').click(function () {
    style.setAttribute('href', path+'docco.min.css');
});
$('#style_idea').click(function () {
    style.setAttribute('href', path+'idea.min.css');
});