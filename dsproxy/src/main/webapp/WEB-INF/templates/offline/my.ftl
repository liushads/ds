<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
我的闭关修炼<br/>
<@a href="/p?p_of/${pid}/my/"/>刷新 </a><br/>
状态：已经修炼${hour}小时${minutes}分钟 <@a href="/p?p_of/${pid}/gt/"/>领取经验</a><br/>
等级:${player.level}<br/>
经验:${player.exp}/${player.dyn.maxExp}<br/>
负重:${player.room}/${player.dyn.maxRoom}<br/>
<@showAllMoney player/>
<@a href="/p?p_of/${pid}/nt/"/>【闭关修炼提示】</a><br/>
<#if !player.playerMember?exists>
【<@a href="/p?p_m/${pid}/p/"/>开通会员</a>每日抽奖可能抽到闭关修炼符】<br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>