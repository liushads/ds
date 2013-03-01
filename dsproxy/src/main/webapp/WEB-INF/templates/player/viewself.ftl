<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<#if memberInfo?exists>
${memberInfo}<br/>
</#if>
<@showPlayer player/>
<@a href="/p?p_LA/${pid}/"/>装备</a>  
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>