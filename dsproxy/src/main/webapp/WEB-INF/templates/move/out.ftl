<#include "/include/header.ftl">
<card title="${game_title}">
<p>
<#if location?exists>
${location}<br/>
</#if>
请选择出城方式:当传送珠的使用次数为0时，请到驿站找传送使者更换.<br/>
<@a href="/p?mv_LC/${pid}/"/>骑马</a>(骑马不消耗您的游戏币，移动速度与您的坐骑有关)<br/>
<@a href="/p?mv_TrL/${pid}/"/>传送</a>(需要耗费您的游戏币兑换传送珠，一键抵达您想去的城市)<br/>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>