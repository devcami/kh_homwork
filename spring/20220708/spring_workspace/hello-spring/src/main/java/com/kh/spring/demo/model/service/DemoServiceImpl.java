package com.kh.spring.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.demo.model.dao.DemoDao;
import com.kh.spring.demo.model.dto.Dev;

/**
 * Service 
 * - SqlSession 생성 / 반환 -> Dao DI받아서 처리
 * - dao요청
 * - transaction -> AOP로 구현
 * 
 * @author camilee
 *
 */
@Service
public class DemoServiceImpl implements DemoService {
	
	@Autowired
	private DemoDao demoDao;
	
	@Override
	public int insertDev(Dev dev) {
		return demoDao.insertDev(dev);
	}
	
	@Override
	public List<Dev> selectDevList() {
		return demoDao.selectDevList();
	}
	
	@Override
	public Dev selectDev(int no) {
		return demoDao.selectDev(no);
	}
	
	@Override
	public int deleteDev(int no) {
		return demoDao.deleteDev(no);
	}
	
	@Override
	public int updateDev(Dev dev) {
		return demoDao.updateDev(dev);
	}
}
