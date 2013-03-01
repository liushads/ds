<#include "/include/header.ftl">
<card title="${game_title}"><p>
活动奖励：<br/><#if partObject?exists>
${partObject.activeDesc}的奖品有：<br/>
${info}<br/>
<@a href="/p?n_IA/${pid}/${nextAction}/${partObject.id}/2"/>领取</a><br/>
</#if>
<#if infoNote?exists>
领取成功：<br/>
恭喜你获得：${infoNote}<br/>
</#if>
<@gofacility/>
<@gogame/>
</p></card>
</wml>