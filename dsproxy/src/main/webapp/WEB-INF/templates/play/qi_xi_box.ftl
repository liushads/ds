<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if player_item?exists>
恭喜你! 你获得<@a href="/p?i_I/${pid}/${player_item.id}/"/>${player_item.item.name}</a>.<br/>
<#elseif desc?exists>
${desc}<br/>
<#else>
抱歉,打开游龙装备箱错误!
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>