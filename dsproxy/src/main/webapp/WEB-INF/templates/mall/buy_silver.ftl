<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
你兑换了${amount}银,花费${gold}金。<br/>
当前账户信息：<br/>
<@showAllMoney player/>
<@gomall/>
<@gogame/>
</p></card>
</wml>