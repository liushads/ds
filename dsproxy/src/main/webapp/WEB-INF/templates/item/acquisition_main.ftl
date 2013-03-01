<#-- 显示玩家所要装备类物品 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if td?exists>
${td}<br/>
</#if>
<@a href="/p?i_AQ/${pid}/caiji"/>继续采集</a>
<br/>
<@goback />
<@gogame/>
</p></card>
</wml> 