package id.co.mii.serverapp.models;

import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "status")
public class Status extends BaseEntity {
  private String name;
}
