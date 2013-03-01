<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
你选择了祝贺【${playerMarry.playerName}】和【${playerMarry.mateName}】的婚宴:<br/>
请选择你的贺礼：<br/>
<@a href="/p?pm_CG/${pid}/${playerMarry.playerId}/1/"/>圆圆满满礼包(2金)</a><br/>
<@a href="/p?pm_CG/${pid}/${playerMarry.playerId}/1/"/>百年好合礼包 (2金)</a><br/>
<@a href="/p?pm_CG/${pid}/${playerMarry.playerId}/2/"/>永结同心礼包(1金)</a><br/>
<@a href="/p?pm_CG/${pid}/${playerMarry.playerId}/3/"/>新婚大喜礼包(500银)</a><br/>
<@a href="/p?pm_CG/${pid}/${playerMarry.playerId}/4/"/>比翼齐飞礼包(200银)</a><br/>
<@a href="/p?pm_CG/${pid}/${playerMarry.playerId}/5/"/>好梦圆圆礼包(100银)</a><br/>
<@a href="/p?pm_CG/${pid}/${playerMarry.playerId}/6/"/>给美金</a><br/>
<@goback />
<@gogame />
</p>
</card>
</wml>