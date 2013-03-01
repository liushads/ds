<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>
中秋佳节集字兑换活动到期取消，中秋礼包开启在9月30号后结束，请有礼包的玩家尽快开启礼包.<br/>
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>