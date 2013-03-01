<#include "/include/header.ftl">
<card title="${game_title}"><p>

<#if (amount<50)>
<#assign gold=0.1>
<#else>
<#assign gold=0.2>
</#if>

【${location}】传送到【${city.name}】<br/>
请选择传送方式:<br/>
<#if chuanSongZhu?exists><@a href="/p?mv_Tr/${pid}/${city.id}/0/"/>使用传送珠(剩余${chuanSongZhu.currEndure}次)</a><#else><@a href="/p?mv_sz/${pid}/f/${city.id}/"/>领取传送珠</a></#if><br/>
<@a href="/p?mv_Tr/${pid}/${city.id}/1/"/>花费0.1金票</a><br/>
<@a href="/p?mv_Tr/${pid}/${city.id}/2/"/>花费0.1金锭</a><br/>
<@goback/>
<@gofacility/>
</p></card>
</wml>