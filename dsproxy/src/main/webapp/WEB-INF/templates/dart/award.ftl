<#macro showAwart awart>
	<#if awart?exists>
		<#if (awart.copper > 0)>
			${awart.copper}金币
		</#if>
		<#if (awart.gold > 0)>
			${awart.gold}钻石
		</#if>
		<#if awart.dartItems?exists>
			<#list awart.dartItems as dartItme>
				<#if (dartItme_index > 0)>、</#if>
				${dartItme.name}X${dartItme.amount}
			</#list>
		</#if>
		<br/>
	</#if>
</#macro>