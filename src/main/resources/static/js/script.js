<script th:inline="javascript">
    var nameJs = /*[[${name}]]*/;
</script>
function showAlert() {
    alert("The button was clicked!");
}
<button> type="button" th:onclick="showAlert()">Show Alert </button>
function showName(name) {
    alert("Here's the name: " + name);
}

<button type="button" th:onclick="showName(nameJs);">Show Name</button>
<script type="text/javascript" th:src="@{/static/actions.js}"></script>