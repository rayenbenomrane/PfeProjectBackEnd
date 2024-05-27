package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dtos.DetailImpotDto;
import com.example.dtos.PeriodeDto;
import com.example.dtos.TypeImpotDto;
import com.example.entity.DetailImpot;
import com.example.entity.TypeImpot;
import com.example.repository.DetailImpotRepository;
import com.example.repository.TypeImpotRepository;
@Service
public class DetailImpotServiceImpl implements DetailImpotService{


	@Autowired
	private TypeImpotRepository typeimpotrepo;
	@Autowired
	private DetailImpotRepository detailrepo;

	@Override
	public DetailImpotDto saveDetailImpot(DetailImpotDto di) {
		TypeImpot   impot= typeimpotrepo.findByLibelle(di.getTypeImpot().getLibelle())
                .orElseThrow(() -> new IllegalArgumentException("Invalid periodicite"));
		DetailImpot detail=new DetailImpot();
		detail.setLibelle(di.getLibelle());
		detail.setCalculable(di.isCalculable());
		detail.setFormule(di.getFormule());
		detail.setObligatoire(di.isObligatoire());
		detail.setOrdre(di.getOrdre());
		detail.setTypeDetail(di.getTypeDetail());
		detail.setTypeImpot(impot);
		DetailImpot detailcree=detailrepo.save(detail);
		DetailImpotDto detaildto=new DetailImpotDto();

		detaildto.setIdDetailImpot(detailcree.getIdDetailImpot());
		detaildto.setLibelle(detailcree.getLibelle());
		detaildto.setCalculable(detailcree.isCalculable());
		detaildto.setFormule(detailcree.getFormule());
		detaildto.setObligatoire(detailcree.isObligatoire());
		detaildto.setOrdre(detailcree.getOrdre());
		detaildto.setTypeDetail(detailcree.getTypeDetail());


	TypeImpotDto savedImpot=new TypeImpotDto();

		savedImpot.setLibelle(detailcree.getTypeImpot().getLibelle());
		PeriodeDto pd=new PeriodeDto();
		pd.setIdPeriodicite(detailcree.getTypeImpot().getPeriodicite().getIdPeriodicite());
		pd.setPeriode(detailcree.getTypeImpot().getPeriodicite().getPeriode());
		savedImpot.setPeriodicite(pd);


		detaildto.setTypeImpot(savedImpot);

		return detaildto;




	}

	@Override
	public List<DetailImpot> findbytypeImpot(String libelle) {
		// TODO Auto-generated method stub
		Optional<TypeImpot> typetrouve=typeimpotrepo.findByLibelle(libelle);
		if(typetrouve.get()!=null) {

			List<DetailImpot> listtrouve=detailrepo.findByTypeImpot(typetrouve.get());
			return listtrouve;
		}else return null;

	}

}
