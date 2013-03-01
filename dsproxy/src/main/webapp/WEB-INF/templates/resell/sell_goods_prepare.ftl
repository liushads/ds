<#include "/include/header.ftl">
<card title="${game_title}"><p>
您将用${sell_price_vo.price}元售出【${sell_price_vo.name}】<br/>
出售1份获利${profit}元   <@a href="/p?rs_SGC/${pid}/1/${goods_index}/${random_code}"/>确定</a><br/>
全部卖出获利${profit_total}元   <@a href="/p?rs_SGC/${pid}/2/${goods_id}/0"/>确定</a><br/>
<@goback/>
<@gogame/>
</p></card>
</wml>