<#include "/include/header.ftl">

<card title="${game_title}"><p>

<@app/>
<#if loginInfo?exists>
${loginInfo}
<#assign gameBaseUrl="http://${lastZone.proxyIp}:${lastZone.proxyPort}/"/>
<@ag href="/p?u_EZ/${uid}/"/>${lastZone.name}</a><br/>
</#if>
<#assign TxBaseUrl="http://mg.3g.qq.com"/>
请选择游戏服务区：<br/>
<#list zones as zone>
	<#assign gameBaseUrl="http://${zone.proxyIp}:${zone.proxyPort}/"/>
		<@ag href="/p?u_EZ/${uid}/"/>${zone.name}<#if zone.noteInfo?exists>(${zone.noteInfo})</#if></a><br/>
</#list>
<#if ouid?exists>
</#if>
<#--
${game_title}交流QQ群：122296898,117121147,121377548,118980136,91435058。我们的GM将随时保持在线与您交流。<br/>
${game_title}客服邮箱：kf3@ppsea.com<br/>
-->
<br/>
<@qqbar/>
</p></card>
</wml>