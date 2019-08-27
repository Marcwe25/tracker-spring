	var compact;
	var switchURL;
	var contextPath;
	
	function switchCompact(url){
		compact = !compact;
		switchURL = url+ "setCompact?compact=" + compact;
		window.location.href = switchURL;
	}

	function setCompact(comp) {
		
		if(comp == undefined || comp == null) {comp=compact}
		var hideSelector1 = '.hide';
		var hideRule1 = 'display: none;'
		var hideSelector2 = 'body';
		var hideRule2 = 'font-size: 2vw;'
		var hideSelector3 = '.container';
		var hideRule3 = 'width: 60%;'
		var style = document.getElementById("compactStyle");
		if (style)
			style = style.sheet;
		if(style!=null){
			if (!comp) {
				if (style.addRule) {
					style.addRule(hideSelector1, hideRule1)
					style.addRule(hideSelector2, hideRule2)
					style.addRule(hideSelector3, hideRule3)
				} else if (style.insertRule) {
					style.insertRule(hideSelector1, hideRule1)
					style.insertRule(hideSelector2, hideRule2)
					style.insertRule(hideSelector3, hideRule3)
				}
			} else {
				style.deleteRule(hideSelector1, hideRule1);
				style.deleteRule(hideSelector2, hideRule2);
				style.deleteRule(hideSelector3, hideRule3);
			}
		}
	}
