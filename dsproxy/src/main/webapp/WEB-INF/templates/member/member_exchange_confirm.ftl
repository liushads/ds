<#include "/include/header.ftl">
<card title="${game_title}"><p>
会员积分换礼品<br/>
<#if pitem?exists>
你确定使用${pitem.points}积分换${pitem.amount}个${pitem.itemName}吗<br/>
<@a href="p?p_m/${pid}/m_ex/${pitem.id}/c"/>确定</a><br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>