function historyPlot(companySymbol, interval) {

    var options = {
        lines: {
            show: true
        },
        points: {
            show: true
        },
        xaxis: {
            mode: "time",
            minTickSize: [1, "hour"]
        }
    };

    var data = [];

    jQuery.plot("#placeholder", data, options);

    // Fetch one series, adding to what we already have
    var alreadyFetched = {};

    // Initiate a recurring data update
    function refreshData() {

        data = [];
        alreadyFetched = {};

        jQuery.plot("#placeholder", data, options);

        function fetchData() {

            function onDataReceived(series) {

                // Load all the data in one pass; if we only got partial
                // data we could merge it with what we already have.

                data = [ series ];
                jQuery.plot("#placeholder", data, options);
            }

            // Normally we call the same URL - a script connected to a
            // database - but in this case we only have static example
            // files, so we need to modify the URL.
            jQuery.ajax({
                url: "/stockCompanies/data/"+companySymbol,
                type: "GET",
                dataType: "json",
                success: onDataReceived
            });
        }

        fetchData();
        window.setInterval(fetchData, interval);
    }
    refreshData();
}