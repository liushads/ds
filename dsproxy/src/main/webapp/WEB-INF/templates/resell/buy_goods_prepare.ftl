<#include "/include/header.ftl">
<card title="${game_title}"><p>
您将用${sell_price_vo.price}元购买【${sell_price_vo.name}】<br/>
购买1份   <@a href="/p?rs_BGC/${pid}/${sell_price_vo.id}/1"/>确定</a><br/>
全部购买   <@a href="/p?rs_BGC/${pid}/${sell_price_vo.id}/${remain_number}"/>确定</a><br/>
<@goback/>
<@gogame/>
</p></card>
</wml>