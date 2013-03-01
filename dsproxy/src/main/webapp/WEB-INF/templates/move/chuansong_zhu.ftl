<#include "/include/header.ftl">
<card title="${game_title}"><p>
您已经成功领取到传送珠，拥有他您可以快速到达目的城市，当传送珠的使用次数为0时，您可以到主城驿站传送使者处更换新的传送珠<br/>
<#if cityId?exists>
<@a href="/p?mv_TrC/${pid}/${cityId}/"/>返回</a>
<#else>
<@goback />
</#if>
<br/>
<@gofacility/>
</p></card>
</wml> 