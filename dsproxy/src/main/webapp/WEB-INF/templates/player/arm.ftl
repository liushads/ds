<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card  id="c1" title="${game_title}"><p>
你当前等级:【${player.level}级】<br/>
<@a href="/p?i_UAA/${pid}//"/>一键穿</a> <@a href="/p?i_UnA/${pid}/all/"/>一键卸</a><br/>
<@viewUseArm objs,player/>
<br/>
<@goStatus/>.<@a href="/p?i_L/${pid}/1"/>物品.</a><br/>
<@gogame/>
</p>
</card>
</wml>

 